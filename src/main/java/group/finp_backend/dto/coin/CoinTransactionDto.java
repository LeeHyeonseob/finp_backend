package group.finp_backend.dto.coin;

import group.finp_backend.dto.user.UserDto;
import group.finp_backend.entity.CoinTransaction;
import group.finp_backend.entity.TransactionType;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class CoinTransactionDto {
    private Long id;
    private BigDecimal amount;
    private TransactionType type;
    private UserDto user;
    private LocalDateTime createdAt;

    public static CoinTransactionDto fromEntity(CoinTransaction transaction){
        return CoinTransactionDto.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .type(transaction.getTransactionType())
                .user(UserDto.fromEntity(transaction.getUser()))
                .createdAt(transaction.getTransactionTime())
                .build();

    }
}
