package group.finp_backend.dto.post;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdatePostDto {
    private Long id;
    private String title;
    private String content;
}
