package ru.yandex.practicum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.yandex.practicum.dto.PostDto;
import ru.yandex.practicum.service.PostService;

import java.util.List;

@Controller
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String getIndexPage() {
        return "redirect:/posts";
    }

    @GetMapping("/posts")
    public String getPostsPage(Model model) {
        List<PostDto> posts = postService.findAll();
        posts.forEach(post -> post.setContent(replaceNewLineWithBrTag(post.getContent())));
        model.addAttribute("posts", posts);
        return "posts";
    }

    @PostMapping("/post/create")
    public String createNewPost(@ModelAttribute PostDto newPost) {
        postService.save(newPost);
        return "redirect:/";
    }

    private String replaceNewLineWithBrTag(String content) {
        return content.replace("\n", "<br>");
    }
}
