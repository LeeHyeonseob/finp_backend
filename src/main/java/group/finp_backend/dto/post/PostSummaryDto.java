package group.finp_backend.dto.post;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostSummaryDto {
    private Long id;
    private String title;

}
