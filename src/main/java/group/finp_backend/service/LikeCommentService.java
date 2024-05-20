package group.finp_backend.service;

import group.finp_backend.dto.favorite.FavoriteDto;
import group.finp_backend.dto.like.LikeCommentDto;
import group.finp_backend.entity.*;
import group.finp_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeCommentService {


    private final LikeCommentRepository likeCommentRepository;


    private final UserRepository userRepository;


    private final CommentRepository commentRepository;

    public LikeCommentDto likeComment(Long userId, Long commentId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        LikeComment likeComment = new LikeComment();
        likeComment.setUser(user);
        likeComment.setComment(comment);

        LikeComment savedLikeComment = likeCommentRepository.save(likeComment);
        return LikeCommentDto.fromEntity(savedLikeComment);
    }

    public void unlikeComment(Long userId, Long commentId) {
        LikeComment likeComment = likeCommentRepository.findByUserIdAndCommentId(userId, commentId)
                .orElseThrow(() -> new RuntimeException("LikeComment not found"));
        likeCommentRepository.delete(likeComment);
    }

    public List<LikeCommentDto> getLikesByCommentId(Long commentId) {
        List<LikeComment> likeComments = likeCommentRepository.findByCommentId(commentId);
        return likeComments.stream()
                .map(LikeCommentDto::fromEntity)
                .toList();
    }
}
