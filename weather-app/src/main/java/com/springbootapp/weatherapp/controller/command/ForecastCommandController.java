package com.springbootapp.weatherapp.controller.command;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootapp.weatherapp.model.ApiResponse;
import com.springbootapp.weatherapp.model.entity.ForecastEntity;
import com.springbootapp.weatherapp.service.ForecastService;
import com.springbootapp.weatherapp.service.kafka.KafkaProducerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/command/forecast")
public class ForecastCommandController {
    @Autowired
    ForecastService forecastService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addForecast(@Valid @RequestBody ForecastEntity forecastEntity) {
        boolean success = forecastService.addForecast(forecastEntity);
        if (success) {
            return new ResponseEntity<>(new ApiResponse("Forecast added successfully"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse("Failed to add forecast"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        ForecastEntity forecastEntity = this.forecastService.getForecastById(id);
        if (forecastEntity != null) {
            this.forecastService.deleteById(id);
            return new ResponseEntity<>(String.format("Forecast %d has been deleted successfully", id), HttpStatus.OK);
        }

        return new ResponseEntity<>(String.format("No forecast found with ID %d", id), HttpStatus.BAD_REQUEST);
    }
}
