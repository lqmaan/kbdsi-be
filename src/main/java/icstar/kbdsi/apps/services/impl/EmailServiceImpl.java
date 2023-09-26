package icstar.kbdsi.apps.services.impl;

import icstar.kbdsi.apps.dto.EmailDetailsDto;
import icstar.kbdsi.apps.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public String sendSimpleMail(EmailDetailsDto emailDetailsDto) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(emailDetailsDto.getRecipient());
            mailMessage.setSubject(emailDetailsDto.getSubject());
            mailMessage.setText(emailDetailsDto.getMsgBody());

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
