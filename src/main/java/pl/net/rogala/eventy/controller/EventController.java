package pl.net.rogala.eventy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import pl.net.rogala.eventy.entity.Event;
import pl.net.rogala.eventy.model.EventDto;
import pl.net.rogala.eventy.model.EventType;
import pl.net.rogala.eventy.model.FindEventDto;
import pl.net.rogala.eventy.service.EventService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class EventController {

    private EventService eventService;
    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/findEvents")
    public String showMainPage(
            @RequestParam("name") String name,
                               @RequestParam("eventType") EventType eventType,
            @ModelAttribute("findEventDto") FindEventDto findEventDto,
            Model model) {
        LocalDateTime localDate= LocalDateTime.now();

        model.addAttribute("eventTypes", EventType.values());
        findEventDto.setName(name);
        findEventDto.setEventType(eventType);
        List<EventDto> events = eventService.getEvents(findEventDto);

        model.addAttribute("events", events);
        return "home";  
    }

}
