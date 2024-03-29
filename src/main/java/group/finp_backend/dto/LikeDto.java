package group.finp_backend.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LikeDto {
    private Long userId;
    private Long commentId;
}
