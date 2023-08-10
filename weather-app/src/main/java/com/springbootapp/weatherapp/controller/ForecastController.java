package com.springbootapp.weatherapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.springbootapp.weatherapp.model.entity.ForecastEntity;
import com.springbootapp.weatherapp.repository.ForecastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/forecast")
@Profile("prod")
public class ForecastController {

    @Autowired
    ForecastRepository forecastRepository;

    @GetMapping("/get/all")
    public ResponseEntity<List<ForecastEntity>> getTutorialById() {
        List<ForecastEntity> forecastEntityData = forecastRepository.findAll(Sort.by(Sort.Order.asc("id")));

        if (!forecastEntityData.isEmpty()) {
            return new ResponseEntity<>(forecastEntityData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ForecastEntity> addForecast(@RequestBody JsonObject json) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            ForecastEntity forecastEntityData = objectMapper.readValue(json.toString(), ForecastEntity.class);

            forecastRepository.save(forecastEntityData);
            return new ResponseEntity<>(forecastEntityData, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> removeForecast(@PathVariable Long id) {
        this.forecastRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/byName/{name}")
    public ResponseEntity<List<ForecastEntity>> getByName(@PathVariable String name) {
        List<ForecastEntity> forecastEntityData = forecastRepository.findByName(name);

        if (!forecastEntityData.isEmpty()) {
            return new ResponseEntity<>(forecastEntityData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
