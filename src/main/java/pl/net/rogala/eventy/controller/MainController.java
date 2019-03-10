package pl.net.rogala.eventy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *  Controller for home page of "Eventy" web application
 */

@Controller
public class MainController {

    @GetMapping("/")
    public String showMainPage(){
        return "mainPage";
    }
}
