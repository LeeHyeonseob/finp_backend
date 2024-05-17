package group.finp_backend.dto.coin;

import group.finp_backend.entity.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoinTransactionCreateDto {
    private BigDecimal amount;
    private TransactionType type;
}
