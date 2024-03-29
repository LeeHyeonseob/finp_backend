package group.finp_backend.dto;

import group.finp_backend.dto.comment.CommentSummaryDto;
import group.finp_backend.dto.post.PostSummaryDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserProfileDto {
    private Long userId;
    private String email;
    private String username;
    private List<PostSummaryDto> posts; // User가 작성한 게시글 목록
    private List<CommentSummaryDto> comments; // User가 작성한 댓글 목록
}
