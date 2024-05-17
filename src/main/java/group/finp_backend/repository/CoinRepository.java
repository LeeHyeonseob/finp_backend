package group.finp_backend.repository;

import group.finp_backend.entity.Coin;
import group.finp_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoinRepository extends JpaRepository<Coin, Long> {
    Optional<Coin> findByUser(User user);
}
