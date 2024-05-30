package group.finp_backend.service;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import group.finp_backend.dto.CoinDto;
import group.finp_backend.entity.Coin;
import group.finp_backend.entity.CoinTransaction;
import group.finp_backend.entity.TransactionType;
import group.finp_backend.entity.User;
import group.finp_backend.repository.CoinRepository;
import group.finp_backend.repository.CoinTransactionRepository;
import group.finp_backend.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CoinService {
    private final CoinRepository coinRepository;
    private final CoinTransactionRepository coinTransactionRepository;
    private final UserRepository userRepository;
    private IamportClient iamportClient;

    @Value("${iamport.api.key}")
    private String apiKey;

    @Value("${iamport.api.secret}")
    private String apiSecret;

    @PostConstruct
    public void init() {
        iamportClient = new IamportClient(apiKey, apiSecret);
    }

    public CoinDto chargeCoin(Long userId, String impUid, int amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        try {
            IamportResponse<Payment> paymentResponse = iamportClient.paymentByImpUid(impUid);
            Payment payment = paymentResponse.getResponse();
            if (payment == null || !payment.getStatus().equals("paid") || payment.getAmount().intValue() != amount) {
                throw new RuntimeException("Invalid payment information");
            }

            Coin coin = user.getCoin();
            if (coin == null) {
                coin = new Coin();
                coin.setUser(user);
                coin.setAmount(0);
            }
            coin.setAmount(coin.getAmount() + amount);

            CoinTransaction transaction = new CoinTransaction();
            transaction.setUser(user);
            transaction.setAmount(amount);
            transaction.setTransactionType(TransactionType.CHARGE);
            transaction.setImpUid(impUid); // impUid 설정

            coinTransactionRepository.save(transaction);
            coinRepository.save(coin);

            return CoinDto.builder()
                    .id(coin.getId())
                    .userId(user.getId())
                    .balance(coin.getAmount())
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Error verifying payment", e);
        }
    }

    public void rewardComment(Long fromUserId, Long toUserId, int amount) {
        User fromUser = userRepository.findById(fromUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        User toUser = userRepository.findById(toUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Coin fromUserCoin = coinRepository.findByUserId(fromUser.getId())
                .orElseThrow(() -> new RuntimeException("Coin not found for user"));
        Coin toUserCoin = coinRepository.findByUserId(toUser.getId())
                .orElseThrow(() -> new RuntimeException("Coin not found for user"));

        // Deduct coins from the rewarder
        CoinTransaction spendTransaction = new CoinTransaction();
        spendTransaction.setUser(fromUser);
        spendTransaction.setAmount(-amount);
        spendTransaction.setTransactionType(TransactionType.SPEND);
        coinTransactionRepository.save(spendTransaction);

        fromUserCoin.setAmount(fromUserCoin.getAmount() - amount);
        coinRepository.save(fromUserCoin);

        // Add coins to the rewardee
        CoinTransaction earnTransaction = new CoinTransaction();
        earnTransaction.setUser(toUser);
        earnTransaction.setAmount(amount);
        earnTransaction.setTransactionType(TransactionType.EARN);
        coinTransactionRepository.save(earnTransaction);

        toUserCoin.setAmount(toUserCoin.getAmount() + amount);
        coinRepository.save(toUserCoin);
    }


}
