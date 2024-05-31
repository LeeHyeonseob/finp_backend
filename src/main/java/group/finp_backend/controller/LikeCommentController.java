package group.finp_backend.controller;

import group.finp_backend.dto.LikeCommentDto;
import group.finp_backend.dto.LikeCommentRequestDto;
import group.finp_backend.service.LikeCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/like-comments")
@RequiredArgsConstructor
@Slf4j
public class LikeCommentController {
    private final LikeCommentService likeCommentService;

    @PostMapping("/{commentId}")
    public ResponseEntity<LikeCommentDto> likeComment(@PathVariable Long commentId, @RequestBody LikeCommentRequestDto request) {
        String username = request.getUsername();

        // 로그 추가
        log.info("Received like request for commentId: {} from username: {}", commentId, username);

        if (username == null) {
            throw new IllegalArgumentException("Username must not be null");
        }

        return ResponseEntity.ok(likeCommentService.likeComment(commentId, username));
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


