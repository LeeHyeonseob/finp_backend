package group.finp_backend.repository;

import group.finp_backend.entity.LikeComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {
    Optional<Object> findByUserIdAndCommentId(Long userId, Long commentId);

    long countByCommentId(Long commentId);
}
