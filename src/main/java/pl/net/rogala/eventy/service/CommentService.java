package pl.net.rogala.eventy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.net.rogala.eventy.entity.Comment;
import pl.net.rogala.eventy.form.CommentEditForm;
import pl.net.rogala.eventy.repository.CommentRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public Optional<Comment> showSingleComment(Long commentId)
    {
       Optional<Comment> comment= commentRepository.findById(commentId);
        return comment;
    }
    public Long getCommentEventId(Long commentId)
    {
        Optional<Comment> comment= commentRepository.findById(Long.valueOf(commentId));
        return  comment.get().getEventId();
    }
    public void editComment(Long commentId, CommentEditForm commentEditForm)
    {
     Comment comment=commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("comment not found"));
        comment.setBody(commentEditForm.getBody());
        commentRepository.save(comment);
    }

}
