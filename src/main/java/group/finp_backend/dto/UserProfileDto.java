package group.finp_backend.dto;

import group.finp_backend.entity.Coin;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Builder
public class UserProfileDto {
    private Long id;
    private String username;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private CoinDto coin;
    private List<PostDto> posts;
    private List<PostDto> favorites;
}
