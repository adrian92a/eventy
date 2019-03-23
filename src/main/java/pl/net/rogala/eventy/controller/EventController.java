package pl.net.rogala.eventy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.net.rogala.eventy.entity.Event;

import java.util.Optional;

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


    @GetMapping("/event/{eventId}")
    public String showSingleEvent(@PathVariable String eventId, Authentication authentication, Model model) {
        Optional<Event> eventOptional = eventService.getSingleEvent(Long.valueOf(eventId));
        if (!eventOptional.isPresent()) {
            return "event/eventNotFound";
        }
        boolean showCommentForm = authentication != null;
        model.addAttribute("showCommentForm", showCommentForm);
        model.addAttribute("event", eventOptional.get());
        model.addAttribute("comments", eventService.getAllCommentsToEvent(Long.parseLong(eventId)));
//        model.addAttribute("showUsers", eventService.showAllUsersAssignedToEvent(Long.parseLong(eventId)));

        return "event/showSingleEvent";
    }

    @PostMapping("/event/{id}/addUserToEvent")
    public String handleNewUserAssignedToEvent(@PathVariable String id, Authentication authentication) {
        eventService.assignedUseToEvent(Long.parseLong(id), authentication.getName());
        return "redirect:/event/" + id;
    }

    @GetMapping("/addEvent")
    public String addNewEvent(Model model) {
        model.addAttribute("newEventForm", new NewEventForm());
        return "event/newEventForm";
    }

    @PostMapping("/addEvent")
    public String handleNewEventForm(@ModelAttribute @Valid NewEventForm newEventForm, BindingResult bindingResult, Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return "event/newEventForm";
        }
        eventService.addNewEvent(newEventForm, authentication);
        return "redirect:/home/";
    }


    @PostMapping("/event/{id}/comment/add")
    public String handleNewCommentForm(@PathVariable String id,
                                       @RequestParam String commentBody,
                                       @RequestParam String eventId,
                                       Authentication authentication
    ) {
        eventService.addNewComment(Long.parseLong(id), authentication.getName(), commentBody);
        return "redirect:/event/" + eventId;
    }


}
