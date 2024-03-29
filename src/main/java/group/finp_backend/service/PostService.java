package group.finp_backend.service;

import group.finp_backend.dto.PostDto;
import group.finp_backend.entity.User;
import group.finp_backend.repository.PostRepository;
import group.finp_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostDto createPost(PostDto createPostDto){
        User user = userRepository.findById(createPostDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User"))
    }

}
