package group.finp_backend.repository;

import group.finp_backend.entity.CoinTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoinTransactionRepository extends JpaRepository {
    List<CoinTransaction> findByUserId(Long userId);
}
