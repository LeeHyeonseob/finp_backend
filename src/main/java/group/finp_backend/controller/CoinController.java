package group.finp_backend.controller;

import group.finp_backend.dto.coin.CoinTransactionDto;
import group.finp_backend.service.CoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/coins")
@RequiredArgsConstructor
public class CoinController {

    private final CoinService coinService;

    @PostMapping("/charge")
    public CoinTransactionDto chargeCoin(@RequestParam Long userId, @RequestParam BigDecimal amount){
        return coinService.chargeCoin(userId, amount);
    }

}
