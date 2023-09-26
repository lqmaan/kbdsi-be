package icstar.kbdsi.apps.repository;

import icstar.kbdsi.apps.models.Reminder;
import icstar.kbdsi.apps.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    Page<Reminder> findByStatusContaining(String status, PageRequest pageable, Sort sort);

}
