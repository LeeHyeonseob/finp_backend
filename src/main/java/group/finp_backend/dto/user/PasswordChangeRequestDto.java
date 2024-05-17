package group.finp_backend.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PasswordChangeRequestDto {
    private String currentPassword;
    private String newPassword;
}
