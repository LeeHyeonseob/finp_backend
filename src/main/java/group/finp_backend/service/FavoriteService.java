package group.finp_backend.service;

import group.finp_backend.dto.FavoriteDto;
import group.finp_backend.entity.Favorite;
import group.finp_backend.entity.Post;
import group.finp_backend.entity.User;
import group.finp_backend.repository.FavoriteRepository;
import group.finp_backend.repository.PostRepository;
import group.finp_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public FavoriteDto addFavorite(FavoriteDto favoriteDto) {
        User user = userRepository.findByUsername(favoriteDto.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findById(favoriteDto.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setPost(post);

        favoriteRepository.save(favorite);

        post.setFavoritesCount(post.getFavoritesCount() + 1);
        postRepository.save(post);

        return FavoriteDto.builder()
                .id(favorite.getId())
                .username(favorite.getUser().getUsername())
                .postId(favorite.getPost().getId())
                .build();
    }

    public void removeFavorite(FavoriteDto favoriteDto) {
        User user = userRepository.findByUsername(favoriteDto.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findById(favoriteDto.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Favorite favorite = favoriteRepository.findByUserIdAndPostId(user.getId(), post.getId())
                .orElseThrow(() -> new RuntimeException("Favorite not found"));

        favoriteRepository.delete(favorite);

        post.setFavoritesCount(post.getFavoritesCount() - 1);
        postRepository.save(post);
    }
}
