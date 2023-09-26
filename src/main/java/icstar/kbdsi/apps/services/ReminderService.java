package icstar.kbdsi.apps.services;

import icstar.kbdsi.apps.models.Reminder;
import icstar.kbdsi.apps.models.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

public interface ReminderService {
    Page<Reminder> findReminderContainingStatus(String status, Integer pageNum, Integer pageSize);


}
