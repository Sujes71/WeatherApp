package com.springbootapp.weatherapp.service.component.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootapp.weatherapp.model.event.ForecastDeleteEvent;
import com.springbootapp.weatherapp.repository.postgresql.ForecastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ForecastDeleteEventListener {

    @Autowired
    private ForecastRepository forecastRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate; // Asegúrate de tener el KafkaTemplate configurado

    @KafkaListener(topics = "forecast-delete-topic")
    public void handleForecastDeleteEvent(ForecastDeleteEvent event) {
        Long forecastId = event.getId();
        forecastRepository.deleteById(forecastId);

        // Generar y publicar el evento ForecastDeleteEvent a través de Kafka
        kafkaTemplate.send("my-topic", "ForecastDeleteEvent:" + serializeEvent(event));
    }

    private String serializeEvent(ForecastDeleteEvent event) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return "ForecastDeleteEvent:" + objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing event.", e);
        }
    }
}

