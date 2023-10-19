package icstar.kbdsi.apps.repository;

import icstar.kbdsi.apps.models.Reminder;
import icstar.kbdsi.apps.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    Page<Reminder> findByStatusContainingAndIsDeleted(String status, boolean isDeleted, PageRequest pageable, Sort sort);

    Page<Reminder> findByDescriptionContainingAndStatusContainingAndIsDeleted(String description, String status, boolean isDeleted, PageRequest pageable, Sort sort);

    List<Reminder> findAllByScheduleDateAndStatusAndIsSendAndIsDeleted(Date scheduleDate, String status, boolean isSend, boolean isDeleted);

    List<Reminder> findAllByStatusAndIsRepeatedAndIsSendAndIsDeleted(String status, boolean isRepeated, boolean isSend, boolean isDeleted);

    List<Reminder> findByStatusContainingAndIsDeleted(String status, boolean isDeleted);

    List<Reminder> findByIsDeleted(boolean isDeleted);

}
