package group.finp_backend.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private Long userId;
}
