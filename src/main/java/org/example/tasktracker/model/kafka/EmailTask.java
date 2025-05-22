package org.example.tasktracker.model.kafka;

import lombok.*;

@Builder
@Getter
@Setter
public class EmailTask {

    private String recipient;
    private String subject;
    private String body;
}
