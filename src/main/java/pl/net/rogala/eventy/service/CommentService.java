package pl.net.rogala.eventy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.net.rogala.eventy.entity.Comment;
import pl.net.rogala.eventy.repository.CommentRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private UserService userService;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserService userService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
    }

    public List<Comment> getAllCommentsToEvent(Long eventId) {
        return commentRepository.findAllByEventId(eventId);
    }

    public void addNewComment(Long eventId, String userEmail, String body) {
        Comment comment = new Comment(null,
                userService.getUser(userEmail).get(),
                LocalDateTime.now(),
                body,
                eventId
                );
        commentRepository.save(comment);

    }


}
