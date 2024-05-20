package group.finp_backend.controller;

import group.finp_backend.dto.user.UserLoginDto;
import group.finp_backend.dto.user.UserRegistrationDto;
import group.finp_backend.entity.User;
import group.finp_backend.service.AuthService;
import group.finp_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        User createdUser = authService.register(userRegistrationDto);
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginDto userLoginDto) {
        String token = authService.login(userLoginDto);
        return ResponseEntity.ok(token);
    }
}
