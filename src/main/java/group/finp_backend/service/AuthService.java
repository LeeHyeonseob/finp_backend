package group.finp_backend.service;

import group.finp_backend.dto.user.UserDto;
import group.finp_backend.dto.user.UserLoginDto;
import group.finp_backend.dto.user.UserRegistrationDto;
import group.finp_backend.entity.User;
import group.finp_backend.repository.UserRepository;
import group.finp_backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;



    //register 구현
    public UserDto register(UserRegistrationDto dto){
        User newUser = new User()
    }
    @Transactional
    public String login(UserLoginDto dto){
        String email = dto.getEmail();
        String password = dto.getPassword();
        User user = userRepository.findUserByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("이메일이 존재하지 않습니다");
        }

        if(!encoder.matches(password, user.getPassword())){
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다");
        }

        UserRegistrationDto regdto = UserRegistrationDto.builder()
                .email(email)
                .password(password)
                .username(user.getUsername())
                .build();
        //유저 정보받아서
        String accessToken = jwtUtil.createAccessToken(regdto);

        return accessToken;
    }
}
