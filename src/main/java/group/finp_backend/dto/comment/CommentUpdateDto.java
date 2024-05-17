package group.finp_backend.dto.comment;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentUpdateDto {
    private String content;
}
