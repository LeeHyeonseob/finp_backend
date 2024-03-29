package group.finp_backend.dto;

import group.finp_backend.entity.TransactionType;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class CoinTransactionDto {
    private Long userId;
    private BigDecimal amount;
    private TransactionType transactionType;
    private LocalDateTime transactionTime;
}
