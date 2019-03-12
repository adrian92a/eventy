package pl.net.rogala.eventy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import pl.net.rogala.eventy.form.UserRegisterForm;
import pl.net.rogala.eventy.service.UserService;

import javax.validation.Valid;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showUserRegisterForm(Model model){
        model.addAttribute("userRegisterForm", new UserRegisterForm());
        return "user/registerUser";
    }

    @PostMapping("/register")
    public String handleUserRegisterForm(@ModelAttribute @Valid UserRegisterForm userRegisterForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "user/registerUser";
        }
        userService.registerUser(userRegisterForm);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(){
        return"user/loginUser";
    }


}
