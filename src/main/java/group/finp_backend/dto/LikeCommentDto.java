package group.finp_backend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class LikeCommentDto {
    private Long id;
    private Long commentId;
    private Long userId;
}
