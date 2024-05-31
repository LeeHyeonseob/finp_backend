package group.finp_backend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;


@Data
@Builder
public class FavoriteDto {
    private Long id;
    private Long postId;
    private String username;
}
