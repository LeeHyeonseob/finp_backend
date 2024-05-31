package group.finp_backend.service;

import group.finp_backend.dto.LikeCommentDto;
import group.finp_backend.entity.Comment;
import group.finp_backend.entity.LikeComment;
import group.finp_backend.entity.User;
import group.finp_backend.repository.CommentRepository;
import group.finp_backend.repository.LikeCommentRepository;
import group.finp_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeCommentService {
    private final LikeCommentRepository likeCommentRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public LikeCommentDto likeComment(Long commentId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Long userId = user.getId();

        if (likeCommentRepository.findByCommentIdAndUserId(commentId, userId).isPresent()) {
            throw new IllegalStateException("Already liked this comment");
        }

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        LikeComment likeComment = new LikeComment();
        likeComment.setComment(comment);
        likeComment.setUser(user);
        likeCommentRepository.save(likeComment);

        return LikeCommentDto.builder()
                .id(likeComment.getId())
                .commentId(commentId)
                .userId(userId)
                .build();
    }

    public void unlikeComment(Long commentId, Long userId) {
        if (commentId == null || userId == null) {
            throw new IllegalArgumentException("Comment ID and User ID must not be null");
        }

        LikeComment likeComment = likeCommentRepository.findByCommentIdAndUserId(commentId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Like not found"));
        likeCommentRepository.delete(likeComment);
    }

    public int countLikes(Long commentId) {
        if (commentId == null) {
            throw new IllegalArgumentException("Comment ID must not be null");
        }

        return likeCommentRepository.countByCommentId(commentId);
    }
}