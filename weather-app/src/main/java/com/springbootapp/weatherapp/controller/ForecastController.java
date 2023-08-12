package com.springbootapp.weatherapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.springbootapp.weatherapp.model.entity.ForecastEntity;
import com.springbootapp.weatherapp.model.entity.MunicipalityEntity;
import com.springbootapp.weatherapp.repository.ForecastRepository;
import com.springbootapp.weatherapp.repository.MunicipalityRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;
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
    ForecastRepository forecastRepository;
    @Autowired
    MunicipalityRepository municipalityRepository;

    @GetMapping("/all")
    public ResponseEntity<List<ForecastEntity>> getTutorialById() {
        List<ForecastEntity> forecastEntityData = forecastRepository.findAll(Sort.by(Sort.Order.asc("id")));

        if (!forecastEntityData.isEmpty()) {
            return new ResponseEntity<>(forecastEntityData, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ForecastEntity> addForecast(@Valid @RequestBody ForecastEntity forecastEntity) {
        MunicipalityEntity municipality = this.municipalityRepository.findByName(forecastEntity.getMunicipality());
        if (municipality == null) {
            municipalityRepository.save(new MunicipalityEntity(forecastEntity.getMunicipality()));
        }
        try {
            forecastRepository.save(forecastEntity);
            return new ResponseEntity<>(forecastEntity, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> removeForecast(@PathVariable Long id) {
        this.forecastRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<ForecastEntity>> getByName(@PathVariable String name) {
        List<ForecastEntity> forecastEntityData = forecastRepository.findByMunicipality(name);

        if (!forecastEntityData.isEmpty()) {
            return new ResponseEntity<>(forecastEntityData, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
