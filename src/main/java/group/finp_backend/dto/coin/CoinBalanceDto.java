package group.finp_backend.dto.coin;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class CoinBalanceDto {

    private Long userId;
    private BigDecimal balance;
}
