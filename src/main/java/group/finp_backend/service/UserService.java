package group.finp_backend.service;

import group.finp_backend.dto.CoinDto;
import group.finp_backend.dto.PostDto;
import group.finp_backend.dto.UserDto;
import group.finp_backend.dto.UserProfileDto;
import group.finp_backend.entity.Coin;
import group.finp_backend.entity.Favorite;
import group.finp_backend.entity.Post;
import group.finp_backend.entity.User;
import group.finp_backend.repository.CoinRepository;
import group.finp_backend.repository.FavoriteRepository;
import group.finp_backend.repository.PostRepository;
import group.finp_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PostRepository postRepository;
    private final CoinRepository coinRepository;
    private final FavoriteRepository favoriteRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public Long getUserIdByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getId();
    }

    public UserProfileDto getUserProfileByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<PostDto> userPosts = user.getPosts().stream()
                .map(post -> PostDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .username(post.getUser().getUsername())
                        .views(post.getViews())
                        .reward(post.getReward())
                        .favoritesCount(post.getFavoritesCount())
                        .createdAt(post.getCreatedAt())
                        .updatedAt(post.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());

        List<PostDto> favoritePosts = user.getFavorites().stream()
                .map(favorite -> PostDto.builder()
                        .id(favorite.getPost().getId())
                        .title(favorite.getPost().getTitle())
                        .content(favorite.getPost().getContent())
                        .username(favorite.getPost().getUser().getUsername())
                        .views(favorite.getPost().getViews())
                        .reward(favorite.getPost().getReward())
                        .favoritesCount(favorite.getPost().getFavoritesCount())
                        .createdAt(favorite.getPost().getCreatedAt())
                        .updatedAt(favorite.getPost().getUpdatedAt())
                        .build())
                .collect(Collectors.toList());

        return UserProfileDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .coin(CoinDto.builder()
                        .id(user.getCoin().getId())
                        .userId(user.getCoin().getUser().getId())
                        .balance(user.getCoin().getAmount())
                        .build())
                .posts(userPosts)
                .favorites(favoritePosts)
                .build();
    }


    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

    public UserDto updateUser(Long userId, UserDto userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        userRepository.save(user);

        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    private PostDto convertToPostDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .username(post.getUser().getUsername())
                .views(post.getViews())
                .reward(post.getReward())
                .favoritesCount(post.getFavoritesCount())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }


}
