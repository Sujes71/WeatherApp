package com.springbootapp.weatherapp.service.serviceimpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootapp.weatherapp.model.collection.ForecastDocument;
import com.springbootapp.weatherapp.model.collection.ProbPrecipitationDocument;
import com.springbootapp.weatherapp.model.dto.ElementDTO;
import com.springbootapp.weatherapp.model.entity.ForecastEntity;
import com.springbootapp.weatherapp.model.event.ForecastAddEvent;
import com.springbootapp.weatherapp.model.event.ForecastDeleteEvent;
import com.springbootapp.weatherapp.repository.mongodb.ForecastMongoRepository;
import com.springbootapp.weatherapp.service.ForecastService;
import com.springbootapp.weatherapp.service.kafka.KafkaProducerService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ForecastServiceImpl implements ForecastService {
    private static final Logger logger = LoggerFactory.getLogger(ForecastServiceImpl.class);
    @Autowired
    ForecastMongoRepository forecastMongoRepository;
    @Autowired
    KafkaProducerService kafkaProducerService;

    @Override
    public List<ElementDTO> getForecasts() {
        List<ForecastDocument> forecastDocuments = forecastMongoRepository.findAll();
        List<ElementDTO> elementDTOS = new ArrayList<>();

        for (ForecastDocument forecastDocument : forecastDocuments) {
            Float probPrecipitation = 0.00F;
            for (ProbPrecipitationDocument value : forecastDocument.getProbPrecipitations()) {
                probPrecipitation += value.getValue();
            }

            probPrecipitation = (float)Math.round(probPrecipitation / forecastDocument.getProbPrecipitations().size());
            ElementDTO elementDTO = ElementDTO.builder()
                    .id(forecastDocument.getId())
                    .municipality(forecastDocument.getMunicipality())
                    .date(forecastDocument.getDate())
                    .avg(forecastDocument.getTemperature().getAvg())
                    .unit(forecastDocument.getTemperature().getUnit())
                    .precipitation_avg(probPrecipitation)
                    .build();
            elementDTOS.add(elementDTO);
        }

        return elementDTOS;
    }
    @Override
    public List<ForecastDocument> getForecastsByMunicipality(String name) {
        return forecastMongoRepository
                .findByMunicipality(name);
    }
    @Override
    public boolean addForecast(@Valid ForecastEntity forecastEntity) {
        if (forecastEntity == null || forecastEntity.getMunicipality() == null) {
            return false;
        }

        ForecastAddEvent event = new ForecastAddEvent(forecastEntity);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String eventJson = objectMapper.writeValueAsString(event);

            kafkaProducerService.sendMessage("my-topic", "ForecastAddEvent:" + eventJson);
            return true;
        } catch (Exception e) {
            logger.error("Error serializing ForecastAddEvent.");
            return false;
        }
    }


    @Override
    public boolean deleteById(Long id) {
        try {
            ForecastDeleteEvent event = new ForecastDeleteEvent(id);

            ObjectMapper objectMapper = new ObjectMapper();
            String eventJson = objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(event);

            kafkaProducerService.sendMessage("my-topic", "ForecastDeleteEvent:" + eventJson);

            return true;
        } catch (Exception e) {
            logger.error("Error serializing ForecastDeleteEvent.");
            return false;
        }
    }

    @Override
    public ForecastDocument getForecastById(Long id) {
        Optional<ForecastDocument> result = this.forecastMongoRepository
                .findById(id.toString());
        return result.orElse(null);
    }
}
