package group.finp_backend.dto.comment;

import group.finp_backend.dto.post.PostDto;
import group.finp_backend.dto.user.UserDto;
import group.finp_backend.entity.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentDto {
    private Long id;
    private String content;
    private UserDto author;
    private Long likeCount;
    private PostDto post;
    private LocalDateTime createdAt;

    public static CommentDto fromEntity(Comment comment){
        return CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .author(UserDto.fromEntity(comment.getUser()))
                .likeCount(comment.getLikeCount())
                .post(PostDto.fromEntity(comment.getPost()))
                .createdAt(comment.getRegTime())
                .build();
    }


}
