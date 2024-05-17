package group.finp_backend.dto.favorite;

import group.finp_backend.dto.post.PostDto;
import group.finp_backend.dto.user.UserDto;
import group.finp_backend.entity.Favorite;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class FavoriteDto {
    private Long id;
    private PostDto post;
    private UserDto user;

    public static FavoriteDto fromEntity(Favorite favorite){
        return FavoriteDto.builder()
                .id(favorite.getId())
                .post(PostDto.fromEntity(favorite.getPost()))
                .user(UserDto.fromEntity(favorite.getUser()))
                .build();
    }

}
