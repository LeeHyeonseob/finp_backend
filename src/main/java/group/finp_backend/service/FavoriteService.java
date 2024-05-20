package group.finp_backend.service;

import group.finp_backend.dto.favorite.FavoriteDto;
import group.finp_backend.dto.post.PostDto;
import group.finp_backend.entity.Favorite;
import group.finp_backend.entity.Post;
import group.finp_backend.entity.User;
import group.finp_backend.repository.CommentRepository;
import group.finp_backend.repository.FavoriteRepository;
import group.finp_backend.repository.PostRepository;
import group.finp_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FavoriteService {


    private final FavoriteRepository favoriteRepository;


    private final UserRepository userRepository;


    private final PostRepository postRepository;

    public FavoriteDto addFavorite(Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setPost(post);

        Favorite savedFavorite = favoriteRepository.save(favorite);
        return FavoriteDto.fromEntity(savedFavorite);
    }

    public void removeFavorite(Long userId, Long postId) {
        Favorite favorite = favoriteRepository.findByUserIdAndPostId(userId, postId)
                .orElseThrow(() -> new RuntimeException("Favorite not found"));
        favoriteRepository.delete(favorite);
    }

    public List<FavoriteDto> getFavoritesByUserId(Long userId) {
        List<Favorite> favorites = favoriteRepository.findByUserId(userId);
        return favorites.stream()
                .map(FavoriteDto::fromEntity)
                .toList();
    }
}
