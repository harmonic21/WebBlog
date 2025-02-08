package ru.yandex.practicum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.CommentDto;
import ru.yandex.practicum.dto.PostDto;
import ru.yandex.practicum.service.CommentService;

@Controller
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/post/{postId}/comment/create")
    public String createComment(@ModelAttribute CommentDto commentDto, @PathVariable("postId") Long postId) {
        commentService.save(commentDto.setPost(new PostDto().setId(postId)));
        return "redirect:/post/%s".formatted(postId);
    }

    @PutMapping("/post/{postId}/comment/update")
    @ResponseBody
    public void updateComment(@RequestParam(name = "id") Long id,
                              @RequestParam(name = "text") String text) {
        commentService.updateCommentText(id, text);
    }
}
