package com.springbootapp.weatherapp.service.component.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootapp.weatherapp.model.entity.ForecastEntity;
import com.springbootapp.weatherapp.model.event.ForecastAddEvent;
import com.springbootapp.weatherapp.repository.postgresql.ForecastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ForecastAddEventListener {

    @Autowired
    private ForecastRepository forecastRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "forecast-add-topic")
    public void handleForecastAddEvent(ForecastAddEvent event) {
        ForecastEntity forecastEntity = event.getForecastEntity();
        forecastRepository.save(forecastEntity);

        // Generar y publicar el evento ForecastAddEvent a través de Kafka
        kafkaTemplate.send("my-topic", "ForecastAddEvent:" + serializeEvent(event));
    }

    private String serializeEvent(ForecastAddEvent event) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return "ForecastAddEvent:" + objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing event.", e);
        }
    }
}
