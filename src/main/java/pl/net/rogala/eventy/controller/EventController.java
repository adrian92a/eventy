package pl.net.rogala.eventy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.net.rogala.eventy.entity.Event;
import pl.net.rogala.eventy.service.EventService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class EventController {

    private EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/findEvents")
    public String showMainPage(@RequestParam("name") String name,
                               @RequestParam("option") String option,
                               Model model) {

        List<Event> eventList= eventService.showEventList();
        model.addAttribute("eventList",eventList);
        return "home";
    }

}
