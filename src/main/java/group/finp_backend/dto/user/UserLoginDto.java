package group.finp_backend.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoginDto {
    private String username;
    private String password;
}
