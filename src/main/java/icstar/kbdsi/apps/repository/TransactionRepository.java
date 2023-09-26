package icstar.kbdsi.apps.repository;

import icstar.kbdsi.apps.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
