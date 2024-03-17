package group.finp_backend.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterRequestDto {
    private String email;
    private String password;
    private String username;
}
