package group.finp_backend.service;

import group.finp_backend.dto.post.PostDto;
import group.finp_backend.entity.Post;
import group.finp_backend.entity.User;
import group.finp_backend.repository.PostRepository;
import group.finp_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostDto createPost(Long userId, PostDto postDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setUser(user);

        Post savedPost = postRepository.save(post);
        return PostDto.fromEntity(savedPost);
    }

    public PostDto getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        post.setViewCount(post.getViewCount() + 1);
        return PostDto.fromEntity(post);
    }

    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(PostDto::fromEntity)
                .toList();
    }

    public void deletePost(Long postId){
        postRepository.deleteById(postId);
    }

}
