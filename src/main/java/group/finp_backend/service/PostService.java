package group.finp_backend.service;

import group.finp_backend.dto.post.CreatePostDto;
import group.finp_backend.dto.post.PostDto;
import group.finp_backend.dto.post.UpdatePostDto;
import group.finp_backend.entity.Post;
import group.finp_backend.entity.Tag;
import group.finp_backend.entity.User;
import group.finp_backend.repository.PostRepository;
import group.finp_backend.repository.TagRepository;
import group.finp_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    @Transactional
    public PostDto createPost(CreatePostDto createPostDto){
        User user = userRepository.findById(createPostDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User"));

        Post post = new Post();
        post.setTitle(createPostDto.getTitle());
        post.setContent(createPostDto.getContent());
        post.setUser(user);
        post.setViewCount(0L);

        List<Tag> tags = tagRepository.findAllById(createPostDto.getTags());
        post.setTags(new ArrayList<>(tags));

        post = postRepository.save(post);

        return mapToDto(post);

    }

    @Transactional(readOnly = true)
    public List<PostDto> getAllPosts(){
        return postRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Transactional
    public PostDto updatePost(Long postId, UpdatePostDto updatePostDto){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + postId));

        post.setTitle(updatePostDto.getTitle());
        post.setContent(updatePostDto.getContent());

        post = postRepository.save(post);
        return mapToDto(post);
    }

    @Transactional
    public void deletePost(Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + postId));
        postRepository.delete(post);
    }

    public List<PostDto> getTopPostsByViewCount(){
        return postRepository.findTop10ByOrderByViewCountDesc(PageRequest.of(0,10))
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }






    private PostDto mapToDto(Post post){
       return PostDto.builder()
               .id(post.getId())
               .title(post.getTitle())
               .content(post.getContent())
               .userId(post.getUser().getId())
               .viewCount(post.getViewCount())
               .build();
    }

}
