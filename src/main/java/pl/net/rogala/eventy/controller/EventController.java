package pl.net.rogala.eventy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.net.rogala.eventy.entity.Event;
import pl.net.rogala.eventy.form.NewEventForm;
import pl.net.rogala.eventy.model.EventDto;
import pl.net.rogala.eventy.model.EventType;
import pl.net.rogala.eventy.model.FindEventDto;
import pl.net.rogala.eventy.service.EventService;
import javax.validation.Valid;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class EventController {

    private EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }


    @GetMapping("/event/{eventId}")
    public String showSingleEvent(@PathVariable String eventId, Model model) {
        Optional<Event> eventOptional = eventService.getSingleEvent(Long.valueOf(eventId));
        if (!eventOptional.isPresent()) {
            return "event/eventNotFound";
        }

        model.addAttribute("event", eventOptional.get());

        return "event/showSingleEvent";
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
