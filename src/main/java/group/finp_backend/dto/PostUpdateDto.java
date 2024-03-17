package group.finp_backend.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostUpdateDto {
    private Long id;
    private String title;
    private String content;
}
