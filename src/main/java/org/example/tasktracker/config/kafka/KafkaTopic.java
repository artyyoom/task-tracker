package org.example.tasktracker.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopic {

    public NewTopic taskTopic() {
        return TopicBuilder.name("task-topic").build();
    }
}
