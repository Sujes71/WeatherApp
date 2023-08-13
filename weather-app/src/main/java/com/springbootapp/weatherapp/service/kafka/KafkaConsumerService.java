package com.springbootapp.weatherapp.service.kafka;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootapp.weatherapp.model.entity.ForecastEntity;
import com.springbootapp.weatherapp.model.entity.MunicipalityEntity;
import com.springbootapp.weatherapp.model.event.ForecastAddEvent;
import com.springbootapp.weatherapp.model.event.ForecastDeleteEvent;
import com.springbootapp.weatherapp.repository.ForecastRepository;
import com.springbootapp.weatherapp.repository.MunicipalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class KafkaConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);
    @Autowired
    private ForecastRepository forecastRepository;

    @Autowired
    private MunicipalityRepository municipalityRepository;

    @KafkaListener(topics = "my-topic", groupId = "my-consumer-group")
    public void receiveMessage(String message) {
        if (message.startsWith("ForecastAddEvent:")) {
            handleAddForecastMessage(message);
        } else if (message.startsWith("ForecastDeleteEvent:")) {
            handleDeleteForecastMessage(message);
        }
    }

    private void handleAddForecastMessage(String message) {
        String json = message.substring("ForecastAddEvent:".length());

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ForecastAddEvent addEvent = objectMapper.readValue(json, ForecastAddEvent.class);

            ForecastEntity forecastEntity = addEvent.getForecastEntity();

            municipalityRepository.save(new MunicipalityEntity(forecastEntity.getMunicipality()));
            forecastRepository.save(forecastEntity);
            logger.info("Aggregate forecast event successfully handled.");
        } catch (Exception e) {
            logger.error("Error handling the aggregate forecast event.", e);
        }
    }

    private void handleDeleteForecastMessage(String message) {
        String json = message.substring("ForecastDeleteEvent:".length());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ForecastDeleteEvent forecastDeleteEvent = objectMapper.readValue(json, ForecastDeleteEvent.class);
            forecastRepository.deleteById(forecastDeleteEvent.getId());
            logger.info("Delete forecast event successfully handled.");
        } catch (Exception e) {
            logger.error("Error handling the delete forecast event.", e);
        }
    }
}
