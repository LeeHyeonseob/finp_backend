package group.finp_backend.dto;

import group.finp_backend.entity.TransactionType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CoinTransactionDto {
    private Long id;
    private Long userId;
    private int amount;
    private TransactionType transactionType;
    private LocalDateTime createdAt;
}
