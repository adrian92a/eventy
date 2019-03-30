package pl.net.rogala.eventy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.net.rogala.eventy.form.CommentEditForm;
import pl.net.rogala.eventy.service.CommentService;

import javax.validation.Valid;

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

    @GetMapping("/comment/{commentId}/editComment")
    public String handleEditCommentForm(@RequestParam Long commentId,
                                       Authentication authentication,Model model) {
    if(commentService.showSingleComment(commentId).get().getCommentator().getEmail()==authentication.getName()) {
        model.addAttribute("commentEditForm", new CommentEditForm());
        model.addAttribute("comment", commentService.showSingleComment(commentId).get());
        return "event/editComment";
    }
        return "redirect:/event/" + commentService.getCommentEventId(Long.valueOf(commentId));
    }
    @PostMapping("/comment/{commentId}/editComment")
    public String handleEditComment(@PathVariable String commentId,
            @ModelAttribute @Valid CommentEditForm commentEditForm ,
                                    BindingResult bindingResult)
    {

        commentService.editComment(Long.valueOf(commentId),commentEditForm);
        return "redirect:/event/" + commentService.getCommentEventId(Long.valueOf(commentId));
    }

}
