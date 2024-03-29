package group.finp_backend.dto.comment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentUpdateDto {
    private Long id;
    private String content;
}
