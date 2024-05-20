package group.finp_backend.service;

import group.finp_backend.dto.user.UserDto;
import group.finp_backend.dto.user.UserRegistrationDto;
import group.finp_backend.dto.user.UserUpdateDto;
import group.finp_backend.entity.User;
import group.finp_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserDto createUser(UserRegistrationDto dto){
        User newUser = new User();
        newUser.setUsername(dto.getUsername());
        newUser.setPassword(dto.getPassword());
        newUser.setEmail(dto.getEmail());

        User savedUser = userRepository.save(newUser);
        return UserDto.fromEntity(savedUser);
    }

    public UserDto updateUser(Long id, UserUpdateDto dto){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        return UserDto.fromEntity(userRepository.save(user));
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public UserDto getUserById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserDto.fromEntity(user);
    }


}
