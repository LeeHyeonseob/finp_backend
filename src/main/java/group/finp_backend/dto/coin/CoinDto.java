package group.finp_backend.dto.coin;

import group.finp_backend.dto.user.UserDto;
import group.finp_backend.entity.Coin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoinDto {
    private Long id;
    private BigDecimal balance;
    private BigDecimal totalEarned;
    private BigDecimal totalSpent;
    private UserDto user;

    public static CoinDto fromEntity(Coin coin) {
        return CoinDto.builder()
                .id(coin.getId())
                .balance(coin.getBalance())
                .totalEarned(coin.getTotalEarned())
                .totalSpent(coin.getTotalSpent())
                .user(UserDto.fromEntity(coin.getUser()))
                .build();
    }
}

