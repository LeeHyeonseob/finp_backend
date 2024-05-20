package group.finp_backend.controller;

import group.finp_backend.dto.post.PostDto;
import group.finp_backend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @PostMapping
    public PostDto createPost(@RequestParam Long userId, @RequestBody PostDto postDto){
        return postService.createPost(userId, postDto);
    }

    @PostMapping("/{postId}")
    public PostDto getPostById(@PathVariable Long postId){
        return postService.getPostById(postId);
    }

    @GetMapping
    public List<PostDto> getAllPosts(){
        return postService.getAllPosts();
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
    }
}
