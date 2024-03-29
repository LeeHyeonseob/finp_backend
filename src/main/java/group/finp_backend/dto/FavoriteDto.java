package group.finp_backend.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FavoriteDto {
    private Long postId;
    private Long userId;

}
