package group.finp_backend.controller;

import group.finp_backend.dto.comment.CommentDto;
import group.finp_backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommnetController {

    private final CommentService commentService;

    @PostMapping
    public CommentDto createComment(@RequestParam Long userId,@RequestParam Long postId, @RequestBody CommentDto commentDto){
        return commentService.createComment(userId, postId, commentDto);
    }

    @GetMapping("/post/{postId}")
    public List<CommentDto> getCommentsByPostId(@PathVariable Long postId){
        return commentService.getCommentsByPostId(postId);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long commentId){
        commentService.deleteComment(commentId);
    }
}
