package ru.yandex.practicum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.CommentDto;
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
    public String getPostsPage(Model model,
                               @RequestParam(required = false, defaultValue = "10", name = "page-size") Integer pageSize,
                               @RequestParam(required = false, defaultValue = "0", name = "page-num") Integer pageNum,
                               @RequestParam(required = false, name = "tags") String tags) {
        List<PostDto> posts = postService.findAll(pageNum, pageSize, tags);
        posts.forEach(post -> post.setContent(replaceNewLineWithBrTag(post.getContent())));
        model.addAttribute("posts", posts)
                .addAttribute("new_post", new PostDto())
                .addAttribute("currentPageNum", pageNum)
                .addAttribute("currentPageSize", pageSize)
                .addAttribute("currentTags", tags);
        return "posts";
    }

    @PostMapping("/post/create")
    public String createNewPost(@ModelAttribute PostDto newPost) {
        postService.save(newPost);
        return "redirect:/";
    }

    @GetMapping("/post/{id}")
    public String getPostInfoById(@PathVariable("id") Long id,
                                  Model model) {
        PostDto post = postService.findById(id);
        post.setContent(replaceNewLineWithBrTag(post.getContent()));
        model.addAttribute("post", post);
        model.addAttribute("new_comment", new CommentDto());
        return "post-detailed";
    }

    @PutMapping("/post/{id}/like")
    @ResponseBody
    public void likePost(@PathVariable("id") Long id) {
        postService.likePostWithId(id);
    }

    @PostMapping("/post/{id}/update")
    public String updatePost(@PathVariable("id") Long id, @ModelAttribute PostDto post) {
        postService.update(post.setId(id));
        return "redirect:/post/%s".formatted(id);
    }

    @PostMapping("/post/{id}/delete")
    public String deletePost(@PathVariable("id") Long id) {
        postService.deleteById(id);
        return "redirect:/";
    }

    private String replaceNewLineWithBrTag(String content) {
        return content.replace("\n", "<br>");
    }
}