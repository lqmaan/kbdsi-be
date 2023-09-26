package icstar.kbdsi.apps.services.impl;

import icstar.kbdsi.apps.dto.EmailDetailsDto;
import icstar.kbdsi.apps.dto.ReminderDetailsDto;
import icstar.kbdsi.apps.repository.ReminderRepository;
import icstar.kbdsi.apps.services.EmailService;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import icstar.kbdsi.apps.util.*;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.antlr.v4.runtime.misc.Utils.readFile;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    private ReminderRepository reminderRepository;

    @Value("${spring.mail.username}")
    private String sender;

    public EmailServiceImpl(ReminderRepository reminderRepository){
        super();
        this.reminderRepository = reminderRepository;
    }

    @Override
    public String sendSimpleMail(EmailDetailsDto emailDetailsDto) {
        try {
//            MimeMessage mailMessage = javaMailSender.createMimeMessage();
//            ReminderDetailsDto data =  reminderRepository.
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(emailDetailsDto.getRecipient());
            mailMessage.setSubject(emailDetailsDto.getSubject());
            String temp = "Hello!\n\n"
                    + "This is a reminder for you.\n"
                    + "Here are the following information\n\n"
                    + "Description :" + emailDetailsDto.getDescription() +"\n"
                    + "Amount :  " + emailDetailsDto.getAmount() +"\n"
                    + "Due Date :  " + emailDetailsDto.getPaymentDate() +"\n"
                    + "We hope you're having a great day!\n\n"
                    + "Best regards,\n"
                    + "Team 14";

            Map<String, Object> variables = new HashMap<>();
            variables.put("description", emailDetailsDto.getDescription());
            variables.put("amount", emailDetailsDto.getAmount());
            variables.put("paymentDate", emailDetailsDto.getPaymentDate());

            mailMessage.setText(temp);

            //mailMessage.setRecipient(Message.RecipientType.TO, InternetAddress.parse(emailDetailsDto.getRecipient()));
            //mailMessage.setText(emailDetailsDto.getMsgBody());

//            String htmlTemplate = Arrays.toString(readFile("reminderTemplate.html"));
//            htmlTemplate = htmlTemplate.replace("${name}", "John Doe");
//            htmlTemplate = htmlTemplate.replace("${message}", "Hello, this is a test email.");
            javaMailSender.send(mailMessage);
            return "Mail sent successfully";
        }
        catch (Exception e) {
            System.out.println(e);
            return "Error while sending email...";
        }
    }

    @Override
    public String sendMailWithAttachment(EmailDetailsDto emailDetailsDto) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

            try {
                mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                mimeMessageHelper.setFrom(sender);
                mimeMessageHelper.setFrom(emailDetailsDto.getRecipient());
                mimeMessageHelper.setText(emailDetailsDto.getMsgBody());
                mimeMessageHelper.setSubject(emailDetailsDto.getSubject());

                FileSystemResource file
                        = new FileSystemResource(
                        new File(emailDetailsDto.getAttachment()));

                mimeMessageHelper.addAttachment(
                        file.getFilename(), file);

                javaMailSender.send(mimeMessage);
                return "Mail sent Successfully";
            } catch (MessagingException e) {
//                throw new RuntimeException(e);
                return "Error while sending mail!!!";
            }
    }
}
