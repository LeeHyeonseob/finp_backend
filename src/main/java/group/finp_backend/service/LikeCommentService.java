package group.finp_backend.service;

import group.finp_backend.entity.Comment;
import group.finp_backend.entity.LikeComment;
import group.finp_backend.entity.User;
import group.finp_backend.repository.CommentRepository;
import group.finp_backend.repository.LikeCommentRepository;
import group.finp_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeCommentService {

    private final LikeCommentRepository likeCommentRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public void toggleLikeOnComment(Long userId, Long commentId){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("Comment not found with id: " + commentId));

        LikeComment existLike = (LikeComment) likeCommentRepository.findByUserIdAndCommentId(userId,commentId).orElse(null);

        if(existLike == null){
            LikeComment likeComment = new LikeComment();
            likeComment.setComment(comment);
            likeComment.setUser(user);
            likeCommentRepository.save(likeComment);

        }else{
            likeCommentRepository.delete(existLike);
        }

    }

    @Transactional(readOnly = true)
    public long countLikesForComment(Long commentId){
        return likeCommentRepository.countByCommentId(commentId);
    }


}
