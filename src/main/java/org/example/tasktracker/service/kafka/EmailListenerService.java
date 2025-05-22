package org.example.tasktracker.service.kafka;

import lombok.RequiredArgsConstructor;
import org.example.tasktracker.model.kafka.EmailTask;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailListenerService {

    private final JavaMailSender mailSender;

    @KafkaListener(topics = "EMAIL-SENDING-TASKS")
    public void listen(EmailTask emailTask) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailTask.getRecipient());
        message.setSubject(emailTask.getSubject());
        message.setText(emailTask.getBody());
        mailSender.send(message);
    }
}
