package group.finp_backend.repository;

import group.finp_backend.entity.Post;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    @Query("SELECT p FROM Post p ORDER BY p.favoriteCount DESC")
    List<Post> findTop10ByFavoritesCount();

    List<Post> findTop10ByOrderByCreatedAtDesc();

    List<Post> findTop10ByOrderByViewCountDesc(PageRequest pageRequest);
}
