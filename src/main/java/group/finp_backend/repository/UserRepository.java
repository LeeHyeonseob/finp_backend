package group.finp_backend.repository;

import group.finp_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    public User findUserByEmail(String email);
}
