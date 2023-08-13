package com.springbootapp.weatherapp.service.serviceimpl;

import com.springbootapp.weatherapp.model.dto.ElementDTO;
import com.springbootapp.weatherapp.model.entity.ForecastEntity;
import com.springbootapp.weatherapp.model.entity.MunicipalityEntity;
import com.springbootapp.weatherapp.model.entity.ProbPrecipitationEntity;
import com.springbootapp.weatherapp.model.event.ForecastAddEvent;
import com.springbootapp.weatherapp.model.event.ForecastDeleteEvent;
import com.springbootapp.weatherapp.repository.ForecastRepository;
import com.springbootapp.weatherapp.repository.MunicipalityRepository;
import com.springbootapp.weatherapp.service.ForecastService;
import com.springbootapp.weatherapp.service.kafka.KafkaConsumerService;
import com.springbootapp.weatherapp.service.kafka.KafkaProducerService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ForecastServiceImpl implements ForecastService {
    private static final Logger logger = LoggerFactory.getLogger(ForecastServiceImpl.class);
    @Autowired
    ForecastRepository forecastRepository;
    @Autowired
    KafkaProducerService kafkaProducerService;

    @Override
    public List<ElementDTO> getForecasts() {
        List<ForecastEntity> forecastEntityData = forecastRepository
                .findAll(Sort.by(Sort.Order.asc("id")));
        List<ElementDTO> elementDTOS = new ArrayList<>();

        if (!forecastEntityData.isEmpty()) {
            for(ForecastEntity forecastEntity: forecastEntityData){
                Float prob_precipitation = 0.00F;
                for (ProbPrecipitationEntity prob : forecastEntity.getProbPrecipitations()){
                    prob_precipitation += prob.getValue();
                }

                prob_precipitation = prob_precipitation / forecastEntity.getProbPrecipitations().size();
                ElementDTO elementDTO = ElementDTO
                        .builder()
                        .id(forecastEntity.getId())
                        .municipality(forecastEntity.getMunicipality())
                        .date(forecastEntity.getDate())
                        .avg(forecastEntity.getTemperature().getAvg())
                        .unit(forecastEntity.getTemperature().getUnit())
                        .precipitation_avg(prob_precipitation)
                        .build();
                elementDTOS.add(elementDTO);
            }
        }
        return elementDTOS;
    }
    @Override
    public List<ForecastEntity> getForecastsByMunicipality(String name) {
        return forecastRepository
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
            // Crear el evento de Pronóstico Eliminado
            ForecastDeleteEvent event = new ForecastDeleteEvent(id);

            // Serializar el evento a JSON
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
    public ForecastEntity getForecastById(Long id) {
        Optional<ForecastEntity> result = this.forecastRepository
                .findById(id);
        return result.orElse(null);
    }
}
