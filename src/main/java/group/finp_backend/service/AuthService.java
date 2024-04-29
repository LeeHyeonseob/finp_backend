package group.finp_backend.service;

import group.finp_backend.config.PasswordEncoderConfig;
import group.finp_backend.dto.LoginRequestDto;
import group.finp_backend.dto.RegisterRequestDto;
import group.finp_backend.entity.User;
import group.finp_backend.repository.UserRepository;
import group.finp_backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;


    @Transactional
    public String login(LoginRequestDto dto){
        String email = dto.getEmail();
        String password = dto.getPassword();
        User user = userRepository.findUserByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("이메일이 존재하지 않습니다");
        }

        if(!encoder.matches(password, user.getPassword())){
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다");
        }

        RegisterRequestDto regdto = RegisterRequestDto.builder()
                .email(email)
                .password(password)
                .username(user.getUsername())
                .build();
        //유저 정보받아서
        String accessToken = jwtUtil.createAccessToken(regdto);

        return accessToken;
    }
}
