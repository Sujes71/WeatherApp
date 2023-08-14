package com.springbootapp.weatherapp.db;

import com.springbootapp.weatherapp.controller.command.ForecastCommandController;
import com.springbootapp.weatherapp.controller.query.ForecastController;
import com.springbootapp.weatherapp.model.ApiResponse;
import com.springbootapp.weatherapp.model.collection.ForecastDocument;
import com.springbootapp.weatherapp.model.dto.ElementDTO;
import com.springbootapp.weatherapp.model.entity.ForecastEntity;
import com.springbootapp.weatherapp.model.entity.ProbPrecipitationEntity;
import com.springbootapp.weatherapp.model.entity.TemperatureEntity;
import com.springbootapp.weatherapp.service.serviceimpl.ForecastServiceImpl;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@Tag("db-units")
@SpringBootTest
public class WeatherAppDbJunitTest {
    @Mock
    private ForecastServiceImpl forecastService;
    @InjectMocks
    private ForecastCommandController forecastCController;

    @InjectMocks
    private ForecastController forecastQController;

    @Test
    void testGetForecasts() {
        List<ElementDTO>  elementDTOS = new ArrayList<>();
        elementDTOS.add(new ElementDTO());
        when(forecastService.getForecasts()).thenReturn(elementDTOS);

        ResponseEntity<List<ElementDTO>> response = this.forecastQController.getForecasts();
        assertEquals(HttpStatus.OK, response.getStatusCode());

        when(forecastService.getForecasts()).thenReturn(new ArrayList<>());

        ResponseEntity<List<ElementDTO>> responseEmpty = this.forecastQController.getForecasts();
        assertEquals(Objects.requireNonNull(responseEmpty.getBody()).size(), 0);
    }
    @Test
    void testGetForecastsByName() {
        List<ForecastDocument> forecastDocuments = new ArrayList<>();
        forecastDocuments.add(new ForecastDocument());
        when(forecastService.getForecastsByMunicipality("Marbella")).thenReturn(forecastDocuments);

        ResponseEntity<List<ForecastDocument>> response = this.forecastQController.getForecastsByMunicipality("Marbella");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void testAddForecast() {
        ForecastEntity forecastEntity= new ForecastEntity();
        forecastEntity.setId(Long.parseLong("1234"));
        forecastEntity.setMunicipality("Marbella");
        forecastEntity.setDate(new Date());

        TemperatureEntity temperature = new TemperatureEntity();
        temperature.setId(Long.parseLong("1"));
        temperature.setAvg((float) 25.5);
        temperature.setUnit("G_CEL");
        forecastEntity.setTemperature(temperature);

        Set<ProbPrecipitationEntity> probPrecipitations = new HashSet<>();
        probPrecipitations.add(new ProbPrecipitationEntity());
        forecastEntity.setProbPrecipitations(probPrecipitations);

        List<ForecastEntity> forecastEntities = new ArrayList<>();
        forecastEntities.add(forecastEntity);

        when(forecastService.addForecast(forecastEntity)).thenReturn(true);

        ResponseEntity<ApiResponse> response1 = this.forecastCController.addForecast(forecastEntity);
        assertEquals(HttpStatus.OK, response1.getStatusCode());

        forecastEntities.add(forecastEntity);

        when(forecastService.addForecast(new ForecastEntity())).thenReturn(true);
        ResponseEntity<ApiResponse> response5 = this.forecastCController.addForecast(new ForecastEntity());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response5.getStatusCode());
    }
}
