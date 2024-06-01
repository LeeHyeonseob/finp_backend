package group.finp_backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CoinChargeRequestDto {
    private String username;
    private String impUid;
    private int amount;
}
