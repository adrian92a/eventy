package pl.net.rogala.eventy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.net.rogala.eventy.entity.Comment;
import pl.net.rogala.eventy.entity.Comment;
import pl.net.rogala.eventy.entity.Event;
import pl.net.rogala.eventy.form.EventEditForm;
import pl.net.rogala.eventy.form.NewEventForm;
import pl.net.rogala.eventy.model.EventDto;
import pl.net.rogala.eventy.model.EventType;
import pl.net.rogala.eventy.model.FindEventDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


import pl.net.rogala.eventy.repository.UserRepository;
import pl.net.rogala.eventy.service.EventService;

import javax.validation.Valid;

import pl.net.rogala.eventy.service.UserContextService;

import java.util.List;
import java.util.Optional;
import java.util.Optional;


@Controller
public class EventController {

    private EventService eventService;
    private UserContextService userContextService;

    @Autowired
    public EventController(EventService eventService, UserContextService userContextService) {
        this.eventService = eventService;
        this.userContextService = userContextService;
    }


    @GetMapping("/event/{eventId}")
    public String showSingleEvent(@PathVariable String eventId, Authentication authentication, Model model) {
        Optional<Event> eventOptional = eventService.getSingleEvent(Long.valueOf(eventId));
        if (!eventOptional.isPresent()) {
            return "event/eventNotFound";
        }

        boolean showEditForm = authentication != null;
        boolean showCommentForm = authentication != null;
        List<Comment> comments = eventOptional.get().getComments();
        comments.sort(((c1,c2)->c2.getAdded().compareTo(c1.getAdded())));
        model.addAttribute("showCommentForm", showCommentForm);
        model.addAttribute("event", eventOptional.get());

        model.addAttribute("comments", eventOptional.get().getComments());
//        model.addAttribute("users", eventService.showAllUsersAssignedToEvent(Long.parseLong(eventId)));
        model.addAttribute("loggedUser", authentication);

        model.addAttribute("assignedToUserEntity", eventService.showAllUsersAssignedToEvent(Long.parseLong(eventId)));

         boolean showAssignedUserToEvent = authentication != null;
        model.addAttribute("showAssignedUserToEvent", showAssignedUserToEvent);
            return "event/showSingleEvent";
    }

    @PostMapping("/event/{id}/addUserToEvent")
    public String handleNewUserAssignedToEvent(@PathVariable String id, Authentication authentication) {
        eventService.assignedUserToEvent(Long.parseLong(id), authentication.getName());
        return "redirect:/event/" + id;
    }

    @PostMapping("/event/{id}/removeUserFromEvent")
    public String removeUserAssignedToEvent(@PathVariable String id, Authentication authentication) {
        eventService.removeUserFromEvent(Long.parseLong(id), authentication.getName());
        return "redirect:/event/" + id;
    }

    @GetMapping("/addEvent")
    public String addNewEvent(Model model) {
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

        return "redirect:/event/" + eventId;
    }

    @PostMapping("/addEvent")
    public String handleNewEventForm(@ModelAttribute @Valid NewEventForm newEventForm, BindingResult bindingResult, Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return "event/newEventForm";
        }
        eventService.addNewEvent(authentication);
        return "/home";

    }

    @GetMapping("/findEvents")
    public String showMainPage(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "eventType", required = false) EventType eventType,
            @RequestParam(value = "ownerName", required = false) String ownerNick,
            @ModelAttribute("findEventDto") FindEventDto findEventDto, Authentication authentication,
            Model model) {
        model.addAttribute("eventTypes", EventType.values());
        findEventDto.setName(name);
        findEventDto.setOwnerName(ownerNick);
        findEventDto.setEventType(eventType);
        List<EventDto> events = eventService.getEvents(findEventDto);

        model.addAttribute(     "events", events);
        model.addAttribute("loggedUser", authentication.getName());
        return "event/findEventsResults";
    }
}
