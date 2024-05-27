package group.finp_backend.controller;

import group.finp_backend.dto.CommentDto;
import group.finp_backend.dto.RewardRequest;
import group.finp_backend.entity.Comment;
import group.finp_backend.service.CoinService;
import group.finp_backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CoinService coinService;

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.createComment(commentDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long id, @RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.updateComment(id, commentDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/reward")
    public ResponseEntity<Void> rewardComment(@PathVariable Long id, @RequestBody RewardRequest rewardRequest) {
        coinService.rewardComment(id, rewardRequest.getFromUserId(), rewardRequest.getAmount());
        return ResponseEntity.ok().build();
    }
}
