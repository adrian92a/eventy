package pl.net.rogala.eventy.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import pl.net.rogala.eventy.api.EventSummary;
import pl.net.rogala.eventy.converter.EventConverter;
import pl.net.rogala.eventy.entity.Comment;
import pl.net.rogala.eventy.entity.Event;
import pl.net.rogala.eventy.repository.CommentRepository;

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
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {


    private EventRepository eventRepository;

    private EventConverter eventConverter;

    private AssignedToEventRepository assignedToEventRepository;


    @Autowired
    private UserService userService;


    private CommentRepository commentRepository;

    @Autowired
    public EventService(EventRepository eventRepository, EventConverter eventConverter, UserService userService, CommentRepository commentRepository, AssignedToEventRepository assignedToEventRepository) {
        this.eventRepository = eventRepository;
        this.eventConverter = eventConverter;
        this.userService = userService;
      this.assignedToEventRepository = assignedToEventRepository;
        this.commentRepository = commentRepository;
       }
    private final QEvent event = QEvent.event;


   

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


    public List<Comment> getAllCommentsToEvent(Long eventId) {
        return commentRepository.findAllByEvent_Id(eventId);
}

    public void addNewComment(Long eventId, String userEmail, String body) {
        Comment comment = new Comment();
        comment.setEvent(eventRepository.findById(eventId).get());
        comment.setAdded(LocalDateTime.now());
        comment.setBody(body);
        comment.setCommentator(userService.getUserByEmail(userEmail).get());
        commentRepository.save(comment);
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

     * adding new event to database
     *
     * @param authentication gives logged user's e-mail
     */
    public void addNewEvent(Authentication authentication) {
        eventRepository.save(eventConverter.prepareNewEvent(authentication));
    }

    public List<Event> findEventsByDateRange(LocalDate startDate, LocalDate stopDate) {
        return eventRepository.findAllByStartDateAfterAndStopDateBefore(startDate, stopDate);
    }

    public List<EventSummary> collectAllEvents() {
        List<EventSummary> eventSummaryList = showEventList().stream()
                .map(event -> new EventSummary(event.getId(), event.getName(), event.getStartDate(), event.getStopDate()))
                .collect(Collectors.toList());
        return eventSummaryList;
    }

    public List<EventSummary> collectEventsByDateRange(String startDate, String stopDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate stop = LocalDate.parse(stopDate, formatter);
        List<EventSummary> eventSummaryListWithDateRange = findEventsByDateRange(start, stop).stream()
                .map(event -> new EventSummary(event.getId(), event.getName(), event.getStartDate(), event.getStopDate()))
                .collect(Collectors.toList());
        return eventSummaryListWithDateRange;

    }


}
