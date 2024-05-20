package group.finp_backend.controller;

import group.finp_backend.dto.CoinChargeRequestDto;
import group.finp_backend.dto.CoinDto;
import group.finp_backend.service.CoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/coins")
@RequiredArgsConstructor
public class CoinController {
    private final CoinService coinService;

    @PostMapping("/recharge")
    public ResponseEntity<CoinDto> recharge(@RequestBody CoinChargeRequestDto request) {
        return ResponseEntity.ok(coinService.chargeCoin(request.getUserId(), request.getAmount()));
    }
}
