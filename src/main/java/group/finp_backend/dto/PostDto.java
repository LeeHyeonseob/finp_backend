package group.finp_backend.dto;

import group.finp_backend.entity.Comment;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;


import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private String username;
    private int views;
    private int reward;
    private int favoritesCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CommentDto> comments;
}
