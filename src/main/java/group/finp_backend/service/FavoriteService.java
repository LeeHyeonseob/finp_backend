package group.finp_backend.service;

import group.finp_backend.dto.post.PostDto;
import group.finp_backend.entity.Favorite;
import group.finp_backend.entity.Post;
import group.finp_backend.entity.User;
import group.finp_backend.repository.FavoriteRepository;
import group.finp_backend.repository.PostRepository;
import group.finp_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public void addFavorite(Long userId, Long postId){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId) );
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + postId));

        boolean exists = favoriteRepository.existsByUserIdAndPostId(userId,postId);
        if(exists){
            throw new IllegalStateException("Favorite already exists for this post and user.");
        }
        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setPost(post);

        favoriteRepository.save(favorite);

    }

    @Transactional
    public void removeFavorite(Long userId, Long postId){
        Favorite favorite = favoriteRepository.findByUserIdAndPostId(userId, postId);
        favoriteRepository.delete(favorite);
    }

    @Transactional(readOnly = true)
    public List<PostDto> getUserFavorite(Long userId){
        List<Favorite> favorites = favoriteRepository.findByUserId(userId);
        return favorites.stream()
                .map(favorite -> mapToDto(favorite.getPost()))
                .collect(Collectors.toList());
    }

    private PostDto mapToDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userId(post.getUser().getId())
                .viewCount(post.getViewCount())
                .build();
    }


}
