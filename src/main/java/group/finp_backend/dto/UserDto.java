package group.finp_backend.dto;

import group.finp_backend.entity.Coin;
import group.finp_backend.entity.Role;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Role role;
    private Coin coin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
