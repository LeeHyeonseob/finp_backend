package group.finp_backend.controller;

import group.finp_backend.dto.user.UserDto;
import group.finp_backend.dto.user.UserRegistrationDto;
import group.finp_backend.dto.user.UserUpdateDto;
import group.finp_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;



    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserUpdateDto userUpdateDto) {
        return userService.updateUser(id, userUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
