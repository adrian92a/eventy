/**
 * Controller for home page of "Eventy" web application
 */

package pl.net.rogala.eventy.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @GetMapping("/")
    public String showMainPage() {
        return "mainPage";
    }

    /**
     * Shows logged user email (login) on "home" website
     * @param model
     * @param authentication
     * @return "home" page template
     */

    @RequestMapping(value = "/home")
    public String home(Model model, Authentication authentication){
        model.addAttribute("loggedUser", authentication.getName());
        return "home";
    }


}
