package group.finp_backend.service;

import group.finp_backend.dto.coin.CoinTransactionDto;
import group.finp_backend.entity.Coin;
import group.finp_backend.entity.CoinTransaction;
import group.finp_backend.entity.TransactionType;
import group.finp_backend.entity.User;
import group.finp_backend.repository.CoinRepository;
import group.finp_backend.repository.CoinTransactionRepository;
import group.finp_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class CoinService {

    private final CoinRepository coinRepository;
    private final CoinTransactionRepository coinTransactionRepository;
    private final UserRepository userRepository;

    public CoinTransactionDto chargeCoin(Long userId, BigDecimal amount){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Coin coin = coinRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Coin not found for user"));

        coin.setBalance(coin.getBalance().add(amount));
        coin.setTotalEarned(coin.getTotalEarned().add(amount));
        coinRepository.save(coin);

        CoinTransaction coinTransaction = new CoinTransaction();
        coinTransaction.setUser(user);
        coinTransaction.setAmount(amount);
        coinTransaction.setTransactionType(TransactionType.CHARGE);
        coinTransaction.setTransactionTime(LocalDateTime.now());

        CoinTransaction savedTransaction = coinTransactionRepository.save(coinTransaction);
        return CoinTransactionDto.fromEntity(savedTransaction);
    }
}
