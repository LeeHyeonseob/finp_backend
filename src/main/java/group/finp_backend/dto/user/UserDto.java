package group.finp_backend.dto.user;

import group.finp_backend.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {
    private Long id;
    private String email;
    private String username;

    public static UserDto fromEntity(User user){
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getEmail())
                .build();
    }
}
