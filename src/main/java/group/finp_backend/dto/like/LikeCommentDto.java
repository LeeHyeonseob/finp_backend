package group.finp_backend.dto.like;

import group.finp_backend.dto.comment.CommentDto;
import group.finp_backend.dto.user.UserDto;
import group.finp_backend.entity.LikeComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeCommentDto {
    private Long id;
    private CommentDto comment;
    private UserDto user;

    public static LikeCommentDto fromEntity(LikeComment likeComment){
        return LikeCommentDto.builder()
                .id(likeComment.getId())
                .comment(CommentDto.fromEntity(likeComment.getComment()))
                .user(UserDto.fromEntity(likeComment.getUser()))
                .build();
    }
}
