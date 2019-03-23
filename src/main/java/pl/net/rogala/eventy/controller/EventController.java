package pl.net.rogala.eventy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.net.rogala.eventy.entity.Event;
import pl.net.rogala.eventy.form.EventEditForm;
import pl.net.rogala.eventy.service.EventService;

import javax.validation.Valid;
import java.util.Optional;
import pl.net.rogala.eventy.form.NewEventForm;


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


        return "event/showSingleEvent";
    }
    @GetMapping("/addEvent")
    public String addNewEvent(Model model){
        model.addAttribute("newEventForm", new NewEventForm());
        return "event/newEventForm";
    }

    @GetMapping("/event/editEvent/{eventId}")
    public String showEventEditForm(@PathVariable String eventId, Model model) {
        Optional<Event> eventOptional = eventService.getSingleEvent(Long.valueOf(eventId));
        if (!eventOptional.isPresent()) {
            return "event/eventNotFound";
        }
        model.addAttribute("event", eventOptional.get());
        model.addAttribute("eventEditForm", new EventEditForm());

        return "event/editEvent";
    }


    @PostMapping("/event/editEvent/{eventId}")
    public String handleEventEditForm(@PathVariable String eventId,
                                      @ModelAttribute @Valid EventEditForm eventEditForm,
                                      BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Optional<Event> eventOptional = eventService.getSingleEvent(Long.valueOf(eventId));
            if (!eventOptional.isPresent()) {
                return "event/eventNotFound";
            }
            model.addAttribute("event", eventOptional.get());
            return "event/editEvent";
        }

        eventService.editEvent(Long.valueOf(eventId), eventEditForm);

        return "redirect:/event/"+eventId;
    }

    @PostMapping("/addEvent")
    public String handleNewEventForm(@ModelAttribute @Valid NewEventForm newEventForm, BindingResult bindingResult, Authentication authentication){
        if(bindingResult.hasErrors()){
            return "event/newEventForm";
        }
        eventService.addNewEvent(newEventForm, authentication);
        return "/home";
    }

    @PostMapping("/event/{id}/comment/add")
    public String handleNewCommentForm(
            @PathVariable String id,
            @RequestParam String commentBody,
            @RequestParam String eventId,
            Authentication authentication
    ) {
        eventService.addNewComment(Long.parseLong(id), authentication.getName(), commentBody);
        return "redirect:/event/" + eventId;
    }
}
