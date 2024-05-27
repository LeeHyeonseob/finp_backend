package group.finp_backend.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RewardRequest {
    private Long fromUserId;
    private int amount;
}
