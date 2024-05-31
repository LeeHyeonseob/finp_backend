package group.finp_backend.service;

import group.finp_backend.JwtTokenProvider;
import group.finp_backend.dto.CommentDto;
import group.finp_backend.dto.PostDto;
import group.finp_backend.entity.Comment;
import group.finp_backend.entity.Post;
import group.finp_backend.entity.User;
import group.finp_backend.repository.PostRepository;
import group.finp_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public List<PostDto> getAllPosts(int page) {
        return postRepository.findAll(PageRequest.of(page, 10))
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        post.setViews(post.getViews() + 1);
        postRepository.save(post);
        return convertToDto(post);
    }

    public PostDto createPost(PostDto postDto, String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setReward(postDto.getReward());
        post.setUser(user);

        Post savedPost = postRepository.save(post);
        return convertToDto(savedPost);
    }

    public PostDto updatePost(Long id, PostDto postDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setReward(postDto.getReward());
        return convertToDto(postRepository.save(post));
    }

    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        postRepository.delete(post);
    }

    public List<PostDto> getFavoriteTop10Posts() {
        return postRepository.findTop10ByOrderByFavoritesCountDesc()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<PostDto> getRecent10Posts() {
        return postRepository.findTop10ByOrderByCreatedAtDesc()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private PostDto convertToDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .reward(post.getReward())
                .views(post.getViews())
                .favoritesCount(post.getFavoritesCount())
                .username(post.getUser().getUsername())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .comments(post.getComments().stream().map(this::convertToDto).collect(Collectors.toList()))
                .build();
    }

    private CommentDto convertToDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .username(comment.getUser().getUsername())
                .postId(comment.getPost().getId())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }
}
