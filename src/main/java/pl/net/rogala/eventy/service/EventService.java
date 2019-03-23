package pl.net.rogala.eventy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.net.rogala.eventy.api.EventSummary;
import pl.net.rogala.eventy.converter.EventConverter;
import pl.net.rogala.eventy.entity.Comment;
import pl.net.rogala.eventy.entity.Event;
import pl.net.rogala.eventy.repository.CommentRepository;
import pl.net.rogala.eventy.repository.EventRepository;

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

    @Autowired
    private UserService userService;

    private CommentRepository commentRepository;

    @Autowired
    public EventService(EventRepository eventRepository, EventConverter eventConverter, UserService userService, CommentRepository commentRepository) {
        this.eventRepository = eventRepository;
        this.eventConverter = eventConverter;
        this.userService = userService;
        this.commentRepository = commentRepository;
    }

    public List<Event> showEventList() {
        return eventRepository.findAll(Sort.by("startDate"));
    }

    public Optional<Event> getSingleEvent(Long eventId) {
        return eventRepository.findById(eventId);
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
