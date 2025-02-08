package ru.yandex.practicum.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CommentDto {
    private Long id;
    private PostDto post;
    private String author;
    private String commentContent;
}
