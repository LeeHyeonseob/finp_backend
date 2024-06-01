package group.finp_backend.controller;

import group.finp_backend.dto.CommentDto;
import group.finp_backend.service.CommentService;
import group.finp_backend.service.CoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.security.Principal;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;
    private final CoinService coinService;

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto) {
        log.info("댓글 생성 요청 받음 - 게시글 ID: {}", commentDto.getPostId());
        log.info("댓글 생성 요청 받음 - 사용자 이름: {}", commentDto.getUsername());
        log.info("댓글 생성 요청 받음 - 내용: {}", commentDto.getContent());
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
    public ResponseEntity<Void> rewardComment(@PathVariable Long id, Principal principal) {
        String fromUsername = principal.getName(); // 현재 로그인한 사용자의 이름을 가져옵니다.
        coinService.rewardComment(fromUsername, id);
        return ResponseEntity.ok().build();
    }
}
