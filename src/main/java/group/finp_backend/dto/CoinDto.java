package group.finp_backend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class CoinDto {
    private Long id;
    private Long userId;
    private int balance;
}
