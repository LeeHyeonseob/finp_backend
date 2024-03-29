package group.finp_backend.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentDto {
    private Long id;
    private String content;
    private Long postId;
    private Long userId;
}
