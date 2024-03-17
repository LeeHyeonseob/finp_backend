package group.finp_backend.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRequestDto {
    private String email;
    private String password;
}
