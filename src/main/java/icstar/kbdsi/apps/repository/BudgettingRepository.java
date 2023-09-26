package icstar.kbdsi.apps.repository;

import icstar.kbdsi.apps.models.Budgeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgettingRepository extends JpaRepository<Budgeting, Long> {
}
