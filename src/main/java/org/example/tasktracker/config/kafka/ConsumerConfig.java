package org.example.tasktracker.config.kafka;


import org.apache.kafka.common.serialization.StringDeserializer;
import org.example.tasktracker.model.kafka.EmailTask;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class ConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ConsumerFactory<String, EmailTask> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(
                org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers);
        config.put(
                org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        config.put(
                org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class);
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, EmailTask.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "org.example.tasktracker.model.kafka");
        config.put(
                        org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, "task-tracker");
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new JsonDeserializer<>(EmailTask.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, EmailTask> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, EmailTask> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
