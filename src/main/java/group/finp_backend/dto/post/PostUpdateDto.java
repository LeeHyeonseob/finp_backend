package group.finp_backend.dto.post;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostUpdateDto {
    private String title;
    private String content;
}
