package com.springbootapp.weatherapp.controller;

import com.springbootapp.weatherapp.model.Municipality;
import com.springbootapp.weatherapp.model.dto.ForecastDTO;
import com.springbootapp.weatherapp.model.dto.TemperatureDTO;
import com.springbootapp.weatherapp.service.AemetService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/aemet/mun")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Aemet", description = "Municipalities operations")
public class AemetController {

    @Autowired
    AemetService aemetService;

    @Operation(summary = "Show all municipalities", description = "Show all municipalities")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/all")
    public ResponseEntity<List<Municipality>> getMunicipalities() {
        List<Municipality> muns = this.aemetService.getMunicipalities();

        if (!muns.isEmpty()) {
            return ResponseEntity.ok(muns);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get the municipality by id", description = "Get the municipality by id")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{id}")
    public ResponseEntity<Municipality> getMunById(@PathVariable String id) {
        Municipality mun = this.aemetService.getMunById(id);

        if (mun != null) {
            return ResponseEntity.ok(mun);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @Operation(summary = "Get the weather forecast for tomorrow", description = "Get the weather forecast for tomorrow")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/prediction/tomorrow/{id}")
    public ResponseEntity<ForecastDTO> getMunTomorrowForecast(@PathVariable String id) {
        ForecastDTO forecastDTO = this.aemetService.getMunTomorrowForecast(id);

        if (forecastDTO != null) {
            return ResponseEntity.ok(forecastDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/conversion/{avg}/{unit}")
    @Hidden
    public ResponseEntity<TemperatureDTO> getConversion(@PathVariable Float avg, @PathVariable String unit) {
        TemperatureDTO temperatureDTO = this.aemetService.getConversion(avg, unit);

        if (temperatureDTO != null) {
            return ResponseEntity.ok(temperatureDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
