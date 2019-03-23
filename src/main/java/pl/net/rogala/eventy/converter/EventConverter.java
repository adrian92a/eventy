package pl.net.rogala.eventy.converter;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import pl.net.rogala.eventy.entity.Event;
import pl.net.rogala.eventy.entity.User;
import pl.net.rogala.eventy.form.NewEventForm;
import pl.net.rogala.eventy.service.UserService;

@Component
public class EventConverter {
    private NewEventForm eventForm;
    private UserService userService;


    /**
     * preparing new event for eventService
     * @param authentication gives logged user's e-mail
     */
    public Event prepareNewEvent(Authentication authentication){
        Event event = new Event();
        event.setName(eventForm.getName());
        event.setDecription(eventForm.getDescription());
        event.setStartDate(eventForm.getStartDate());
        event.setStopDate(eventForm.getStopDate());
        User owner = userService.getUserByEmail(authentication.getName()).get();
        event.setOwner(owner);
        return event;
    }
}
