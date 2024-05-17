package group.finp_backend.dto.post;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PostCreateDto {
    private String title;
    private String content;

}
