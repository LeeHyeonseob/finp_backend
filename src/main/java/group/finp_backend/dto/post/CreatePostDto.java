package group.finp_backend.dto.post;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CreatePostDto {
    private String title;
    private String content;
    private Long userId;
    private List<Long> tags;
}
