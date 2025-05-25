package org.example.tasktracker.service.kafka;

import jakarta.mail.Message;
import lombok.RequiredArgsConstructor;
import org.example.tasktracker.model.kafka.EmailTask;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailListenerService {

    @Value("${spring.mail.username}")
    private String username;

    private final JavaMailSender mailSender;

    @KafkaListener(topics = "EMAIL-SENDING-TASKS", groupId = "task-tracker")
    public void listen(EmailTask emailTask) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(username);
            message.setTo(emailTask.getRecipient());
            message.setSubject(emailTask.getSubject());
            message.setText(emailTask.getBody());

            mailSender.send(message);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
