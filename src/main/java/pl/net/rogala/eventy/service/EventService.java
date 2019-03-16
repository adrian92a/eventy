package pl.net.rogala.eventy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.net.rogala.eventy.entity.Event;
import pl.net.rogala.eventy.entity.User;
import pl.net.rogala.eventy.form.NewEventForm;
import pl.net.rogala.eventy.repository.EventRepository;
import pl.net.rogala.eventy.repository.UserRepository;

import java.util.List;

@Service
public class EventService {

    private EventRepository eventRepository;

    private UserRepository userRepository;

    private NewEventForm eventForm;

    @Autowired
    public EventService(EventRepository eventRepository, UserRepository userRepository, NewEventForm eventForm) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.eventForm = eventForm;
    }

    public List<Event> showEventList(){
        return eventRepository.findAll(Sort.by("startDate"));
    }

    /**
     * adding new event to database; setting logged user as owner of addied event
     * @param authentication
     */
    public void addNewEvent(NewEventForm eventForm, Authentication authentication){
        Event event = new Event();
        event.setName(eventForm.getName());
        event.setDecription(eventForm.getDescription());
        event.setStartDate(eventForm.getStartDate());
        event.setStopDate(eventForm.getStopDate());
        User owner = userRepository.findByEmail(authentication.getName()).get();
        event.setOwner(owner);
        Long ownerId = owner.getId();
        userRepository.settingRoleAsOrganizer(2L, ownerId);
        eventRepository.save(event);
    }
}
