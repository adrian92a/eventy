package pl.net.rogala.eventy.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.net.rogala.eventy.entity.*;

import pl.net.rogala.eventy.model.EventDto;
import pl.net.rogala.eventy.model.EventType;
import pl.net.rogala.eventy.model.FindEventDto;
import pl.net.rogala.eventy.repository.AssignedToEventRepository;
import pl.net.rogala.eventy.form.NewEventForm;
import pl.net.rogala.eventy.form.EventEditForm;
import pl.net.rogala.eventy.repository.EventRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class EventService {

    private EventRepository eventRepository;
    private AssignedToEventRepository assignedToEventRepository;

    private UserService userService;

    private final QEvent event = QEvent.event;

    @Autowired
    public EventService(EventRepository eventRepository, AssignedToEventRepository assignedToEventRepository, UserService userService) {
        this.eventRepository = eventRepository;
        this.assignedToEventRepository = assignedToEventRepository;
        this.userService = userService;
    }

    public List<Event> showEventList() {
        return eventRepository.findAll(Sort.by("startDate"));
    }

    public List<EventDto> getEvents(FindEventDto findEventDto) {

        System.out.println("Name: " + findEventDto.getName() + "     Event Type:" + findEventDto.getEventType());
        BooleanExpression booleanExpression = Expressions.asBoolean(true).isTrue();
        if (findEventDto.getName() != null) {
            booleanExpression = booleanExpression.and(event.name.containsIgnoreCase(findEventDto.getName()));
        }
        if (findEventDto.getEventType() != null) {
            if (findEventDto.getEventType().equals(EventType.CURRENT)) {

                booleanExpression = booleanExpression.and(event.startDate.after(LocalDate.now()).and(event.startDate.before(LocalDate.now())));
            }
            if (findEventDto.getEventType().equals(EventType.FUTURE)) {
                booleanExpression = booleanExpression.and(event.startDate.after(LocalDate.now()));
            }
        }

        List<Event> all = eventRepository.findAll(booleanExpression);
        return all.stream().map(Event::toEventDto).collect(Collectors.toList());
    }

    public Optional<Event> getSingleEvent(Long eventId) {
        return eventRepository.findById(eventId);
    }

    public void editEvent(Long eventId, EventEditForm eventEditForm) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("event not found"));

        event.setName(eventEditForm.getName());
        event.setDecription(eventEditForm.getDescription());
        event.setStartDate(eventEditForm.getStartDate());
        event.setStopDate(eventEditForm.getStopDate());

        eventRepository.save(event);
    }

    public List<AssignedToEvent> showAllUsersAssignedToEvent(Long eventId) {
        return assignedToEventRepository.findAllByEventId(eventId);
    }

    public void assignedUserToEvent(Long eventId, String userName) {
        AssignedToEvent assignedToEvent = new AssignedToEvent();
        assignedToEvent.setEvent(eventRepository.findById(eventId).get());
        assignedToEvent.setAddedDate(LocalDateTime.now());
        assignedToEvent.setUser(userService.getUser(userName).get());
        assignedToEventRepository.save(assignedToEvent);
    }

    public void removeUserFromEvent(Long eventId, String userEmail) {
        Long userId = userService.getUser(userEmail).get().getId();
        assignedToEventRepository.removeRecord(userId, eventId);
    }

    /**
     * adding new event to database; setting logged user as owner of added event
     *
     * @param authentication gives logged user's e-mail
     * @param eventForm      form to adding new event
     */
    public void addNewEvent(NewEventForm eventForm, Authentication authentication) {
        Event event = new Event();
        event.setName(eventForm.getName());
        event.setDecription(eventForm.getDescription());
        event.setStartDate(eventForm.getStartDate());
        event.setStopDate(eventForm.getStopDate());
        User owner = userService.getUser(authentication.getName()).get();
        event.setOwner(owner);
        userService.addOrganizerRole(owner);
        eventRepository.save(event);
    }


}
