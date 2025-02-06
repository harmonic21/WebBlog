package ru.yandex.practicum.service;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.CommentDto;
import ru.yandex.practicum.dto.FiltrationParams;
import ru.yandex.practicum.dto.PaginationParams;
import ru.yandex.practicum.dto.PostDto;
import ru.yandex.practicum.dto.domain.Post;
import ru.yandex.practicum.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Component
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void save(PostDto post) {
        postRepository.save(
                new Post()
                        .setTitle(post.getTitle())
                        .setContent(post.getContent())
                        .setTags(splitTags(post.getTags()))
                        .setImage(post.getImage())
                        .setLikesCount(post.getLikesCount())
        );
    }

    public void update(PostDto post) {
        postRepository.update(
                new Post()
                        .setId(post.getId())
                        .setTitle(post.getTitle())
                        .setContent(post.getContent())
                        .setTags(splitTags(post.getTags()))
                        .setImage(post.getImage())
                        .setLikesCount(post.getLikesCount())
        );
    }

    public List<PostDto> findAll(Integer pageNum, Integer pageSize, String tags) {
        return postRepository.findAll(new PaginationParams(pageNum, pageSize), new FiltrationParams(tags)).stream()
                .map(this::mapToDto)
                .toList();
    }

    public PostDto findById(Long id) {
        Post postDomain = postRepository.findById(id);
        return mapToDto(postDomain);
    }

    public void likePostWithId(Long id) {
        postRepository.likePostWithId(id);
    }

    public void deleteById(Long id) {
        postRepository.delete(id);
    }

    private String[] splitTags(String tags) {
        return Optional.ofNullable(tags)
                .map(t -> t.split(";"))
                .orElse(null);
    }

    private String concatTags(String[] tags) {
        return Optional.ofNullable(tags).map(t -> String.join(";", t)).orElse(null);
    }

    private PostDto mapToDto(Post post) {
        var postDto = new PostDto()
                .setId(post.getId())
                .setTitle(post.getTitle())
                .setContent(post.getContent())
                .setTags(concatTags(post.getTags()))
                .setImage(post.getImage())
                .setLikesCount(post.getLikesCount());
        var comments = post.getComments().stream()
                .map(commentDomain -> new CommentDto()
                        .setId(commentDomain.getId())
                        .setPost(postDto)
                        .setAuthor(commentDomain.getAuthor())
                        .setCommentContent(commentDomain.getCommentContent()))
                .toList();
        return postDto.setComments(comments);
    }
}