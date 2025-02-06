package ru.yandex.practicum.dto;

import java.util.Objects;

public class PostDto {
    private Long id;
    private String title;
    private String content;
    private String tags;
    private String image;
    private Long likesCount;

    public Long getId() {
        return id;
    }

    public PostDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public PostDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public PostDto setContent(String content) {
        this.content = content;
        return this;
    }

    public String getTags() {
        return tags;
    }

    public PostDto setTags(String tags) {
        this.tags = tags;
        return this;
    }

    public String getImage() {
        return image;
    }

    public PostDto setImage(String image) {
        this.image = image;
        return this;
    }

    public Long getLikesCount() {
        return likesCount;
    }

    public PostDto setLikesCount(Long likesCount) {
        this.likesCount = likesCount;
        return this;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostDto postDto)) return false;

        return Objects.equals(id, postDto.id) && Objects.equals(title, postDto.title) && Objects.equals(content, postDto.content) && Objects.equals(image, postDto.image) && Objects.equals(likesCount, postDto.likesCount);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(title);
        result = 31 * result + Objects.hashCode(content);
        result = 31 * result + Objects.hashCode(image);
        result = 31 * result + Objects.hashCode(likesCount);
        return result;
    }

    @Override
    public String toString() {
        return "PostDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", image='" + image + '\'' +
                ", likesCount=" + likesCount +
                '}';
    }
}
