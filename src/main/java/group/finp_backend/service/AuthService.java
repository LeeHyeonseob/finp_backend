package group.finp_backend.service;


import group.finp_backend.dto.*;
import group.finp_backend.entity.Coin;
import group.finp_backend.entity.Role;
import group.finp_backend.entity.User;
import group.finp_backend.repository.UserRepository;
import group.finp_backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UserCoinDto register(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.USER);

        // Create a new Coin entity and associate it with the user
        Coin coin = new Coin(user);
        user.setCoin(coin);

        userRepository.save(user);

        CoinDto coinDto = CoinDto.builder()
                .id(coin.getId())
                .userId(coin.getUser().getId())
                .balance(coin.getAmount())
                .build();

        return UserCoinDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .coin(coinDto)
                .build();
    }

    public AuthResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword()));

        User user = userRepository.findByUsername(loginRequestDto.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.createToken(user.getUsername());

        return new AuthResponseDto(token);
    }
}
