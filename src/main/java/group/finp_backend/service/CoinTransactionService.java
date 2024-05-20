package group.finp_backend.service;

import group.finp_backend.dto.CoinTransactionDto;
import group.finp_backend.repository.CoinTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CoinTransactionService {
    private final CoinTransactionRepository coinTransactionRepository;

    public List<CoinTransactionDto> getUserTransactions(Long userId) {
        return coinTransactionRepository.findByUserId(userId).stream()
                .map(transaction -> CoinTransactionDto.builder()
                        .id(transaction.getId())
                        .userId(transaction.getUser().getId())
                        .transactionType(transaction.getTransactionType())
                        .amount(transaction.getAmount())
                        .createdAt(transaction.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
