package pl.net.rogala.eventy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.net.rogala.eventy.converter.EventConverter;
import pl.net.rogala.eventy.entity.Comment;
import pl.net.rogala.eventy.entity.Event;
import pl.net.rogala.eventy.repository.CommentRepository;
import pl.net.rogala.eventy.repository.EventRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
}
