package group.finp_backend.dto.favorite;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FavoriteDto {
    private Long postId;
    private Long userId;

}
