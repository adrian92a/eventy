package pl.net.rogala.eventy.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.net.rogala.eventy.entity.Event;
import pl.net.rogala.eventy.entity.Role;
import pl.net.rogala.eventy.entity.User;
import pl.net.rogala.eventy.form.UserRegisterForm;
import pl.net.rogala.eventy.repository.RoleRepository;
import pl.net.rogala.eventy.repository.UserRepository;

@Getter
@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private boolean logged;

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
     * Register new user in database
     *
     * @param userRegisterForm
     */

// TODO: Check if given in userRegisterForm email and nick are present in database (both should be unique)
    public void registerUser(UserRegisterForm userRegisterForm) {
        User user = new User();
        user.setEmail(userRegisterForm.getEmail());
        user.setNick(userRegisterForm.getNick());
        user.setPassword(passwordEncoder.encode(userRegisterForm.getPassword()));
        setDefaultRole(user);
        userRepository.save(user);
    }




}