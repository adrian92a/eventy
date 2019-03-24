package pl.net.rogala.eventy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.net.rogala.eventy.entity.Event;
import pl.net.rogala.eventy.form.EventEditForm;
import pl.net.rogala.eventy.form.NewEventForm;
import pl.net.rogala.eventy.model.EventDto;
import pl.net.rogala.eventy.model.EventType;
import pl.net.rogala.eventy.model.FindEventDto;
import pl.net.rogala.eventy.service.CommentService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/event/{id}/comment/add")
    public String handleNewCommentForm(@PathVariable String id,
                                       @RequestParam String commentBody,
                                       @RequestParam String eventId,
                                       Authentication authentication
    ) {
        commentService.addNewComment(Long.parseLong(id), authentication.getName(), commentBody);
        return "redirect:/event/" + eventId;
    }
}
