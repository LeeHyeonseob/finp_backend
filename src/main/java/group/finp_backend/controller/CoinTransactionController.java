package group.finp_backend.controller;

import group.finp_backend.dto.CoinTransactionDto;
import group.finp_backend.service.CoinTransactionService;
import group.finp_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coin-transactions")
@RequiredArgsConstructor
public class CoinTransactionController {
    private final CoinTransactionService coinTransactionService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<CoinTransactionDto>> getUserTransactions(@RequestParam Long userId) {
        return ResponseEntity.ok(coinTransactionService.getUserTransactions(userId));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<List<CoinTransactionDto>> getUserTransactionsByUsername(@PathVariable String username) {
        Long userId = userService.getUserIdByUsername(username);
        return ResponseEntity.ok(coinTransactionService.getUserTransactions(userId));
    }
}
