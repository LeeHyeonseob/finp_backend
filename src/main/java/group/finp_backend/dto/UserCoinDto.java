package group.finp_backend.dto;

import group.finp_backend.entity.Role;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserCoinDto {
    private Long id;
    private String username;
    private String email;
    private Role role;
    private CoinDto coin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
