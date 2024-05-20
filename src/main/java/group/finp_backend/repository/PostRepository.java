package group.finp_backend.repository;

import group.finp_backend.entity.Post;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    List<Post> findTop10ByOrderByFavoritesCountDesc();
    List<Post> findTop10ByOrderByCreatedAtDesc();
}
