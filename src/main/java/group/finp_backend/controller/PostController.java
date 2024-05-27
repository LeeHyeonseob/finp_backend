package group.finp_backend.controller;

import group.finp_backend.dto.PostDto;
import group.finp_backend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(@RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(postService.getAllPosts(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @RequestHeader("Authorization") String token) {
        String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        PostDto createdPost = postService.createPost(postDto, jwtToken);
        if (createdPost != null) {
            return ResponseEntity.ok(createdPost);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long id, @RequestBody PostDto postDto) {
        return ResponseEntity.ok(postService.updatePost(id, postDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<PostDto>> getFavoriteTop10Posts() {
        return ResponseEntity.ok(postService.getFavoriteTop10Posts());
    }

    @GetMapping("/recent")
    public ResponseEntity<List<PostDto>> getRecent10Posts() {
        return ResponseEntity.ok(postService.getRecent10Posts());
    }
}