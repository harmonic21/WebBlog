package ru.yandex.practicum.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.yandex.practicum.dto.CommentDto;
import ru.yandex.practicum.dto.FiltrationParams;
import ru.yandex.practicum.dto.PaginationParams;
import ru.yandex.practicum.dto.PostDto;
import ru.yandex.practicum.dto.domain.Post;
import ru.yandex.practicum.repository.PostRepository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private static final String BASE_64_IMAGE_TEMPLATE = "data:image/jpeg;base64,%s";

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void save(PostDto post, MultipartFile image) {
        postRepository.save(
                new Post()
                        .setTitle(post.getTitle())
                        .setContent(post.getContent())
                        .setTags(splitTags(post.getTags()))
                        .setImage(imageToBase64(image))
                        .setLikesCount(post.getLikesCount())
        );
    }

    public void update(PostDto post, MultipartFile image) {
        postRepository.update(
                new Post()
                        .setId(post.getId())
                        .setTitle(post.getTitle())
                        .setContent(post.getContent())
                        .setTags(splitTags(post.getTags()))
                        .setImage(imageToBase64(image))
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

    private String imageToBase64(MultipartFile file) {
        try {
            var encodedImage = new String(Base64.getEncoder().encode(file.getBytes()), StandardCharsets.UTF_8);
            return encodedImage.isBlank() ? encodedImage : BASE_64_IMAGE_TEMPLATE.formatted(encodedImage);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
