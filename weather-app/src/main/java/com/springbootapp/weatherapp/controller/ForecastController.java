package com.springbootapp.weatherapp.controller;

import com.springbootapp.weatherapp.model.dto.ElementDTO;
import com.springbootapp.weatherapp.model.entity.ForecastEntity;
import com.springbootapp.weatherapp.service.ForecastService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/forecast")
@Profile("prod")
public class ForecastController {
    @Autowired
    ForecastService forecastService;

    @GetMapping("/all")
    public ResponseEntity<List<ElementDTO>> getForecasts() {
        List<ElementDTO> result = forecastService.getForecasts();

        if (!result.isEmpty()) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/municipality/{name}")
    public ResponseEntity<List<ForecastEntity>> getForecastsByMunicipality(@PathVariable String name) {
        List<ForecastEntity> result = this.forecastService.getForecastsByMunicipality(name);

        if (!result.isEmpty()) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping(path = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ForecastEntity> addForecast(@Valid @RequestBody ForecastEntity forecastEntity) {
        ForecastEntity result = this.forecastService.addForecast(forecastEntity);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
