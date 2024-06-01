package group.finp_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RewardRequest {
    private String fromUsername;
    private int amount;
}
