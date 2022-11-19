package budgetsolve.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {
    @Value("${mail.email}")
    private String email;
    @Autowired
    public JavaMailSender emailSender;

    public ResponseEntity<Object> sendEmail(String targetEmail) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(email);
        message.setTo(targetEmail);
        message.setSubject("THE BEST BOOK SHOP - > BOOKSHOP !! ");
        message.setText("Hello, it's just a notification about your subscribe");
        try {
            emailSender.send(message);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception ex){
            log.error(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<Object> sendEmail(String targetEmail, String code) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(email);
        message.setTo(targetEmail);
        message.setSubject("THE INSPIRIT TEAM");
        message.setText("Ваш код для відновлення паролю : \n\n\n" + code);
        try {
            emailSender.send(message);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception ex){
            log.error(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
