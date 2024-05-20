package group.finp_backend.service;

import group.finp_backend.JwtTokenProvider;
import group.finp_backend.dto.user.UserLoginDto;
import group.finp_backend.dto.user.UserRegistrationDto;
import group.finp_backend.entity.Role;
import group.finp_backend.entity.User;
import group.finp_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;



    public User register(UserRegistrationDto dto) {
        User newUser = new User();
        newUser.setUsername(dto.getUsername());
        newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        newUser.setEmail(dto.getEmail());
        newUser.setRole(Role.USER);
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(newUser);
    }

    public String login(UserLoginDto dto) {
        User user = userRepository.findByUsername(dto.getUsername())
                .filter(u -> passwordEncoder.matches(dto.getPassword(), u.getPassword()))
                .orElseThrow(() -> new RuntimeException("Login failed"));

        // Generate JWT token here
        String token = jwtTokenProvider.createToken(user.getUsername(), user.getRole());
        return token;
    }

}
