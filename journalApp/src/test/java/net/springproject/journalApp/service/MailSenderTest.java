package net.springproject.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;


@SpringBootTest
public class MailSenderTest {
    @Autowired
    private EmailService emailService;
    @Test
    public void testSendEmail() {
        emailService.sendEmail("l.com", "asdasdasdasd","asdasdasdasda");
    }
}
