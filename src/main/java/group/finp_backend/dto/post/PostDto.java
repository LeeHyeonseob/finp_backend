package group.finp_backend.dto.post;

import group.finp_backend.dto.user.UserDto;
import group.finp_backend.entity.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private UserDto author;
    private Long viewCount;
    private Long favoriteCount;
    private LocalDateTime createdAt;

    public static PostDto fromEntity(Post post){
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(UserDto.fromEntity(post.getAuthor()))
                .favoriteCount(post.getFavoriteCount())
                .viewCount(post.getViewCount())
                .createdAt(post.getRegTime())
                .build();

    }
}
