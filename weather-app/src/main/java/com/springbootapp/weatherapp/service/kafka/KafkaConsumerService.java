package com.springbootapp.weatherapp.service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootapp.weatherapp.model.collection.ForecastDocument;
import com.springbootapp.weatherapp.model.collection.ProbPrecipitationDocument;
import com.springbootapp.weatherapp.model.collection.TemperatureDocument;
import com.springbootapp.weatherapp.model.entity.ForecastEntity;
import com.springbootapp.weatherapp.model.entity.MunicipalityEntity;
import com.springbootapp.weatherapp.model.entity.ProbPrecipitationEntity;
import com.springbootapp.weatherapp.model.entity.TemperatureEntity;
import com.springbootapp.weatherapp.model.event.ForecastAddEvent;
import com.springbootapp.weatherapp.model.event.ForecastDeleteEvent;
import com.springbootapp.weatherapp.repository.mongodb.ForecastMongoRepository;
import com.springbootapp.weatherapp.repository.postgresql.ForecastRepository;
import com.springbootapp.weatherapp.repository.postgresql.MunicipalityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class KafkaConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    @Autowired
    private ForecastRepository forecastRepository;

    @Autowired
    private MunicipalityRepository municipalityRepository;

    @Autowired
    private ForecastMongoRepository forecastMongoRepository; // Asegúrate de tener el repositorio de MongoDB

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

            // Crear una instancia de ForecastDocument y mapear los valores de la entidad
            ForecastDocument forecastDocument = new ForecastDocument();
            forecastDocument.setId(forecastEntity.getId().toString()); // Convertir el ID a ObjectId
            forecastDocument.setDate(forecastEntity.getDate());
            forecastDocument.setMunicipality(forecastEntity.getMunicipality());

            // Mapear la entidad TemperatureEntity a un objeto de TemperatureDocument
            TemperatureEntity temperatureEntity = forecastEntity.getTemperature();
            TemperatureDocument temperatureDocument = new TemperatureDocument();
            temperatureDocument.setUnit(temperatureEntity.getUnit());
            temperatureDocument.setAvg(temperatureEntity.getAvg());
            forecastDocument.setTemperature(temperatureDocument);

            // Mapear las entidades ProbPrecipitationEntity a objetos de ProbPrecipitationDocument
            Set<ProbPrecipitationDocument> probPrecipitationDocuments = new HashSet<>();
            for (ProbPrecipitationEntity prob : forecastEntity.getProbPrecipitations()){
                ProbPrecipitationDocument probPrecipitationDocument = new ProbPrecipitationDocument();
                probPrecipitationDocument.setPeriod(prob.getPeriod());
                probPrecipitationDocument.setValue(prob.getValue());
                probPrecipitationDocuments.add(probPrecipitationDocument);
            }
            forecastDocument.setProbPrecipitations(probPrecipitationDocuments);

            // Guardar el ForecastDocument en MongoDB
            forecastMongoRepository.save(forecastDocument);

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

            forecastMongoRepository.deleteById(forecastDeleteEvent.getId().toString());

            logger.info("Delete forecast event successfully handled.");
        } catch (Exception e) {
            logger.error("Error handling the delete forecast event.", e);
        }
    }
}

