package group.finp_backend.dto;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CommentDto {
    private Long id;
    private String content;
    private Long postId;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int likeCount;
}