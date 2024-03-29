package group.finp_backend.repository;

import group.finp_backend.entity.Favorite;
import group.finp_backend.service.FavoriteService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    boolean existsByUserIdAndPostId(Long userId, Long postId);


    Favorite findByUserIdAndPostId(Long userId, Long postId);

    List<Favorite> findByUserId(Long userId);
}
