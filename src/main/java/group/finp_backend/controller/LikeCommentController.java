package group.finp_backend.controller;

import group.finp_backend.dto.like.LikeCommentDto;
import group.finp_backend.service.LikeCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/like-comments")
@RequiredArgsConstructor
public class LikeCommentController {

    private final LikeCommentService likeCommentService;

    @PostMapping
    public LikeCommentDto likecomment(@RequestParam Long userId, @RequestParam Long commentId){
        return likeCommentService.likeComment(userId, commentId);
    }

    @DeleteMapping
    public void unlikeComment(@RequestParam Long userId, @RequestParam Long commentId){
        likeCommentService.unlikeComment(userId, commentId);
    }

    @GetMapping("/comment/{commentId}")
    public List<LikeCommentDto> getLikesByCommentId(@PathVariable Long commentId){
        return likeCommentService.getLikesByCommentId(commentId);
    }

}
