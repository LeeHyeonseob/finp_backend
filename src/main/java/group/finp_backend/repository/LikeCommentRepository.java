package group.finp_backend.repository;

import group.finp_backend.entity.LikeComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {
    List<LikeComment> findByCommentId(Long commentId);
    Optional<LikeComment> findByCommentIdAndUserId(Long commentId, Long userId);
    int countByCommentId(Long commentId);
}
