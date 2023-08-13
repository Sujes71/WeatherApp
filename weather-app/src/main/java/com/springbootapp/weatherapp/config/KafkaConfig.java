package com.springbootapp.weatherapp.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@EnableKafka
public class KafkaConfig {

    // Define un nuevo tema de Kafka
    @Bean
    public NewTopic myTopic() {
        return TopicBuilder.name("my-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
