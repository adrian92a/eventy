package pl.net.rogala.eventy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.net.rogala.eventy.form.NewEventForm;
import pl.net.rogala.eventy.service.EventService;

import javax.validation.Valid;

@Controller
public class EventController {

    private EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/addEvent")
    public String addNewEvent(Model model){
        model.addAttribute("newEventForm", new NewEventForm());
        return "event/newEventForm";
    }

    @PostMapping("/addEvent")
    public String handleNewEventForm(@ModelAttribute @Valid NewEventForm newEventForm, BindingResult bindingResult, Authentication authentication){
        if(bindingResult.hasErrors()){
            return "event/newEventForm";
        }
        eventService.addNewEvent(newEventForm, authentication);
        return "/home";
    }

}
