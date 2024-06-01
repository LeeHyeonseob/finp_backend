package group.finp_backend.service;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import group.finp_backend.dto.CoinDto;
import group.finp_backend.entity.*;
import group.finp_backend.repository.*;
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
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private IamportClient iamportClient;

    @Value("${iamport.api.key}")
    private String apiKey;

    @Value("${iamport.api.secret}")
    private String apiSecret;

    @PostConstruct
    public void init() {
        iamportClient = new IamportClient(apiKey, apiSecret);
    }

    public CoinDto chargeCoin(String username, String impUid, int amount) {
        User user = userRepository.findByUsername(username)
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
            transaction.setImpUid(impUid);

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

    public void rewardComment(String fromUsername, Long commentId) {
        User fromUser = userRepository.findByUsername(fromUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        User toUser = comment.getUser();

        Post post = comment.getPost();
        int rewardAmount = post.getReward();

        Coin fromUserCoin = coinRepository.findByUserId(fromUser.getId())
                .orElseThrow(() -> new RuntimeException("Coin not found for user"));
        Coin toUserCoin = coinRepository.findByUserId(toUser.getId())
                .orElseThrow(() -> new RuntimeException("Coin not found for user"));

        // Deduct coins from the rewarder
        CoinTransaction spendTransaction = new CoinTransaction();
        spendTransaction.setUser(fromUser);
        spendTransaction.setAmount(-rewardAmount);
        spendTransaction.setTransactionType(TransactionType.SPEND);
        spendTransaction.setImpUid(null); // 보상 로직에서는 impUid를 설정하지 않습니다.
        coinTransactionRepository.save(spendTransaction);

        fromUserCoin.setAmount(fromUserCoin.getAmount() - rewardAmount);
        coinRepository.save(fromUserCoin);

        // Add coins to the rewardee
        CoinTransaction earnTransaction = new CoinTransaction();
        earnTransaction.setUser(toUser);
        earnTransaction.setAmount(rewardAmount);
        earnTransaction.setTransactionType(TransactionType.EARN);
        earnTransaction.setImpUid(null); // 보상 로직에서는 impUid를 설정하지 않습니다.
        coinTransactionRepository.save(earnTransaction);

        toUserCoin.setAmount(toUserCoin.getAmount() + rewardAmount);
        coinRepository.save(toUserCoin);
    }
}
