package pl.net.rogala.eventy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.net.rogala.eventy.entity.Role;
import pl.net.rogala.eventy.entity.User;
import pl.net.rogala.eventy.form.UserRegisterForm;
import pl.net.rogala.eventy.repository.RoleRepository;
import pl.net.rogala.eventy.repository.UserRepository;


import java.util.Optional;
import java.util.Optional;
import java.util.Set;


@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    /**
     * Setting default role ("ROLE_USER" for each new register user
     *
     * @param user
     */
    public void setDefaultRole(User user) {
        Role role = roleRepository.findRoleByRoleName("ROLE_USER").orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));
        user.addRole(role);
    }

    /**
     * Adding new role (organizer) to User's Set of Roles
     *
     * @param user owner of added event
     */
//
    public void addOrganizerRole(User user){
        Role role = roleRepository.findRoleByRoleName("ROLE_ORGANIZER").get();
        Set<Role> roles = user.getRoles();
        roles.add(role);
    }

    public Optional<User> getUserByEmail(String email){
       return userRepository.findByEmail(email);
    }

    /**
     * Register new user in database
     *
     * @param userRegisterForm
     */

    public void registerUser(UserRegisterForm userRegisterForm) {
        Optional<User> userByEmail = userRepository.findByEmail(userRegisterForm.getEmail());
        if (!userByEmail.isPresent()) {
            User user = new User();
            user.setEmail(userRegisterForm.getEmail());
            user.setNick(userRegisterForm.getNick());
            user.setPassword(passwordEncoder.encode(userRegisterForm.getPassword()));
            setDefaultRole(user);
            userRepository.save(user);
        } else {
            System.out.println("Taki użytkownik istnieje w bazie");
        }
    }

}
