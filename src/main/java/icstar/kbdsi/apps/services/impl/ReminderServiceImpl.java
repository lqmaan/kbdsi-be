package icstar.kbdsi.apps.services.impl;

import icstar.kbdsi.apps.models.Reminder;
import icstar.kbdsi.apps.models.User;
import icstar.kbdsi.apps.repository.ReminderRepository;
import icstar.kbdsi.apps.repository.UserRepository;
import icstar.kbdsi.apps.services.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ReminderServiceImpl implements ReminderService {
    @Autowired
    ReminderRepository reminderRepository;

    public ReminderServiceImpl(ReminderRepository reminderRepository){
        super();
        this.reminderRepository = reminderRepository;
    }
    public Page<Reminder> findReminderContainingStatus(String status, Integer pageNum, Integer pageSize){
        Page<Reminder> reminders = reminderRepository.findByStatusContaining(status, PageRequest.of(pageNum, pageSize), Sort.by("description").descending());
        return  reminders;
    }
}
