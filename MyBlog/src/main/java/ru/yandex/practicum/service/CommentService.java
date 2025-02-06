package ru.yandex.practicum.service;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.CommentDto;
import ru.yandex.practicum.dto.domain.Comment;
import ru.yandex.practicum.repository.CommentRepository;

@Component
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void save(CommentDto commentDto) {
        commentRepository.save(
                new Comment()
                        .setPostId(commentDto.getPost().getId())
                        .setAuthor(commentDto.getAuthor())
                        .setCommentContent(commentDto.getCommentContent())
        );
    }
}
