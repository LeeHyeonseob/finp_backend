package group.finp_backend.controller;

import group.finp_backend.dto.LikeCommentDto;
import group.finp_backend.service.LikeCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/like-comments")
@RequiredArgsConstructor
public class LikeCommentController {
    private final LikeCommentService likeCommentService;

    @PostMapping("/{commentId}")
    public ResponseEntity<LikeCommentDto> likeComment(@PathVariable Long commentId, @RequestParam Long userId) {
        return ResponseEntity.ok(likeCommentService.likeComment(commentId, userId));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> unlikeComment(@PathVariable Long commentId, @RequestParam Long userId) {
        likeCommentService.unlikeComment(commentId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count/{commentId}")
    public ResponseEntity<Integer> countLikes(@PathVariable Long commentId) {
        return ResponseEntity.ok(likeCommentService.countLikes(commentId));
    }
}
