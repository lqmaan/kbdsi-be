package icstar.kbdsi.apps.controllers;

import icstar.kbdsi.apps.dto.EmailDetailsDto;
import icstar.kbdsi.apps.dto.ReminderDetailsDto;
import icstar.kbdsi.apps.dto.ReminderDto;
import icstar.kbdsi.apps.dto.UserDto;
import icstar.kbdsi.apps.models.Reminder;
import icstar.kbdsi.apps.models.Transaction;
import icstar.kbdsi.apps.models.User;
import icstar.kbdsi.apps.repository.ReminderRepository;
import icstar.kbdsi.apps.repository.TransactionRepository;
import icstar.kbdsi.apps.services.EmailService;
import icstar.kbdsi.apps.services.ReminderService;
import icstar.kbdsi.apps.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ReminderController {
    @Autowired
    ReminderRepository reminderRepository;

    ReminderService reminderService;

    EmailService emailService;

    public ReminderController(ReminderService reminderService, EmailService emailService) {
        super();
        this.reminderService = reminderService;
        this.emailService =  emailService;
    }

    @GetMapping("/reminders")
    public ResponseEntity<List<Reminder>> getAllReminders (@RequestParam(required = false) String name){
        try{
            List<Reminder> reminders = new ArrayList<Reminder>();

            if(name == null)
                reminders.addAll(reminderRepository.findAll());
//            else
//                categoryRepository.findAllByName(name, PageRequest.of(0, 10, Sort.by("name").ascending()));
            if(reminders.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(reminders, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/reminders")
    public ResponseEntity<Reminder> createReminder(@RequestBody ReminderDetailsDto reminderDetailsDto){
        try{
            Date schDate = new SimpleDateFormat("dd/MM/yyyy").parse(reminderDetailsDto.getScheduleDate());
            Date payDate = new SimpleDateFormat("dd/MM/yyyy").parse(reminderDetailsDto.getPaymentDate());
            Reminder newReminder =  new Reminder(reminderDetailsDto.getEmail(), reminderDetailsDto.getDescription(), reminderDetailsDto.getAmount(), reminderDetailsDto.isRepeated(), reminderDetailsDto.getCreatedBy(), reminderDetailsDto.getStatus(), schDate, payDate);
            reminderRepository.save(newReminder);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(new Reminder(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/reminders/{id}")
    public ResponseEntity<Reminder> updateReminderById(@PathVariable(required = true) Long id, @RequestBody Reminder newReminder) {
        Optional<Reminder> optReminder = reminderRepository.findById(id);
        if(optReminder.isPresent()) {
            Reminder oldReminder = optReminder.get();
            oldReminder.setDescription(newReminder.getDescription());
            oldReminder.setAmount(newReminder.getAmount());
            oldReminder.setRepeated(newReminder.isRepeated());
            oldReminder.setPaymentDate(newReminder.getPaymentDate());
            oldReminder.setScheduleDate(newReminder.getScheduleDate());
            reminderRepository.save(oldReminder);
            return new ResponseEntity<>(oldReminder, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/reminders/{id}")
    public ResponseEntity<String> deleteReminder(@PathVariable(required = true) Long id) {
        try {
            Optional<Reminder> optReminder = reminderRepository.findById(id);
            if(optReminder.isPresent()){
                Reminder oldReminder = optReminder.get();
                oldReminder.setDeleted(true);
            }
            return new ResponseEntity<>("Reminder has been deleted",HttpStatus.OK);
        }catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reminders/status")
    private Page<Reminder> getRemindersWithStatus(@RequestBody ReminderDto reminderDto) {
        Page<Reminder> reminders = reminderService.findReminderContainingStatus(reminderDto.getStatus(), reminderDto.getPageNum(), reminderDto.getPageSize());
        return reminders;
    }


    // Sending a simple Email
    @PostMapping("/reminders/sendmail")
    public String sendMail(@RequestBody EmailDetailsDto emailDetailsDto)
    {
        String status = emailService.sendSimpleMail(emailDetailsDto);

        return status;
    }
}
