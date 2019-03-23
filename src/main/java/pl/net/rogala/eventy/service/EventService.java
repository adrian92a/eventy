package pl.net.rogala.eventy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.net.rogala.eventy.api.EventSummary;
import pl.net.rogala.eventy.converter.EventConverter;
import pl.net.rogala.eventy.entity.AssignedToEvent;
import pl.net.rogala.eventy.entity.Comment;
import pl.net.rogala.eventy.entity.Event;
import pl.net.rogala.eventy.entity.User;
import pl.net.rogala.eventy.form.EventEditForm;
import pl.net.rogala.eventy.repository.AssignedToEventRepository;
import pl.net.rogala.eventy.repository.CommentRepository;
import pl.net.rogala.eventy.repository.EventRepository;
import pl.net.rogala.eventy.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {


    private EventRepository eventRepository;
    private EventConverter eventConverter;
    private UserRepository userRepository;
    private AssignedToEventRepository assignedToEventRepository;

    private UserService userService;
    private CommentRepository commentRepository;

    @Autowired
    public EventService(EventRepository eventRepository, EventConverter eventConverter, UserRepository userRepository, AssignedToEventRepository assignedToEventRepository, UserService userService, CommentRepository commentRepository) {
        this.eventRepository = eventRepository;
        this.eventConverter = eventConverter;
        this.userRepository = userRepository;
        this.assignedToEventRepository = assignedToEventRepository;
        this.userService = userService;
        this.commentRepository = commentRepository;
    }

    public List<Event> showEventList() {
        return eventRepository.findAll(Sort.by("startDate"));
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

    public List<User> showAllUsersAssignedToEvent(Long eventId) {
        return assignedToEventRepository.findAllUsersAssignedToEventById(eventId);
    }

    public void assignedUserToEvent(Long eventId, String userName) {
        AssignedToEvent assignedToEvent = new AssignedToEvent();
        assignedToEvent.setEvent(eventRepository.findById(eventId).get());
        assignedToEvent.setAddedDate(LocalDateTime.now());
        assignedToEvent.setUser(userRepository.findByEmail(userName).get());
        assignedToEventRepository.save(assignedToEvent);
    }

    public void removeUserFromEvent(Long eventId, String userEmail) {

        Long userId = userRepository.findByEmail(userEmail).get().getId();
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

    public List<Event> showAllEventsOrganizedByUser(Authentication authentication) {
        return eventRepository.findEventsByOwner(userService.getUserByEmail(authentication.getName()).get());
    }

    public List<Event> showAllEventsWhereUserContribute(Authentication authentication) {
        List<AssignedToEvent> preparedList = assignedToEventRepository.findAllByUserId(userService.getUserByEmail(authentication.getName()).get().getId());
        return preparedList.stream().map(ae -> ae.getEvent()).collect(Collectors.toList());
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
