package com.springbootapp.weatherapp.db;

import com.springbootapp.weatherapp.controller.ForecastController;
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
    private ForecastController forecastController;

    @Test
    void testGetForecasts() {
        List<ElementDTO>  elementDTOS = new ArrayList<>();
        elementDTOS.add(new ElementDTO());
        when(forecastService.getForecasts()).thenReturn(elementDTOS);

        ResponseEntity<List<ElementDTO>> response = this.forecastController.getForecasts();
        assertEquals(HttpStatus.OK, response.getStatusCode());

        when(forecastService.getForecasts()).thenReturn(new ArrayList<>());

        ResponseEntity<List<ElementDTO>> responseEmpty = this.forecastController.getForecasts();
        assertEquals(HttpStatus.NOT_FOUND, responseEmpty.getStatusCode());
    }
    @Test
    void testGetForecastsByName() {
        List<ForecastEntity> forecastEntities = new ArrayList<>();
        forecastEntities.add(new ForecastEntity());
        when(forecastService.getForecastsByMunicipality("Marbella")).thenReturn(forecastEntities);

        ResponseEntity<List<ForecastEntity>> response = this.forecastController.getForecastsByMunicipality("Marbella");
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

        when(forecastService.addForecast(forecastEntity)).thenReturn(forecastEntity);
        when(forecastService.getForecastsByMunicipality("Marbella")).thenReturn(forecastEntities);

        ResponseEntity<ForecastEntity> response1 = this.forecastController.addForecast(forecastEntity);
        ResponseEntity<List<ForecastEntity>> response2 = this.forecastController.getForecastsByMunicipality("Marbella");
        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertEquals(Objects.requireNonNull(response2.getBody()).size(), 1);

        forecastEntities.add(forecastEntity);
        ResponseEntity<List<ForecastEntity>> response4 = this.forecastController.getForecastsByMunicipality("Marbella");
        assertEquals(Objects.requireNonNull(response4.getBody()).size(), 2);

        when(forecastService.addForecast(new ForecastEntity())).thenReturn(new ForecastEntity());
        ResponseEntity<ForecastEntity> response5 = this.forecastController.addForecast(new ForecastEntity());
        assertEquals(HttpStatus.BAD_REQUEST, response5.getStatusCode());
    }
}
