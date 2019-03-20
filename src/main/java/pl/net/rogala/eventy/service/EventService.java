package pl.net.rogala.eventy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.net.rogala.eventy.entity.AssignedToEvent;
import pl.net.rogala.eventy.entity.Comment;
import pl.net.rogala.eventy.entity.Event;
import pl.net.rogala.eventy.repository.AssignedToEventRepository;
import pl.net.rogala.eventy.repository.CommentRepository;
import pl.net.rogala.eventy.entity.User;
import pl.net.rogala.eventy.form.NewEventForm;
import pl.net.rogala.eventy.repository.EventRepository;
import pl.net.rogala.eventy.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private EventRepository eventRepository;
    private UserRepository userRepository;
    private AssignedToEventRepository assignedToEventRepository;

    private UserService userService;

    private NewEventForm eventForm;
    private CommentRepository commentRepository;

    @Autowired
    public EventService(EventRepository eventRepository, UserRepository userRepository, UserService userService, NewEventForm eventForm, CommentRepository commentRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.eventForm = eventForm;
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
        comment.setCommentator(userRepository.findByEmail(userEmail).get());
        commentRepository.save(comment);

    }

    public void assignedUseToEvent(Long eventId, String userEmail, Boolean orAssigned) {
        AssignedToEvent assignedToEvent = new AssignedToEvent();
        assignedToEvent.setEvent(eventRepository.findById(eventId).get());
        assignedToEvent.setAddedDate(LocalDateTime.now());
        assignedToEvent.setOrAssigned(orAssigned);
        assignedToEvent.setUser(userRepository.findByEmail(userEmail).get());
        assignedToEventRepository.save(assignedToEvent);

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
        User owner = userRepository.findByEmail(authentication.getName()).get();
        event.setOwner(owner);
//        userRepository.addOrganizerRoleForEventOwner(owner.getId());
        userService.addOrganizerRole(owner);
        eventRepository.save(event);
    }
}
