package ru.yandex.practicum.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private String tags;
    private String image;
    private Long likesCount;
    private List<CommentDto> comments;
}
