package group.finp_backend.repository;

import group.finp_backend.entity.Post;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    public List<Post> findTop10ByOrderByViewCountDesc(PageRequest pageRequest);
}
