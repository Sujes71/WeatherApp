package com.springbootapp.weatherapp.controller;

import com.springbootapp.weatherapp.model.Municipality;
import com.springbootapp.weatherapp.model.dto.ReportDTO;
import com.springbootapp.weatherapp.model.dto.TemperatureDTO;

import com.springbootapp.weatherapp.service.AemetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AemetController {

    @Autowired
    AemetService aemetService;

    @GetMapping("/muns")
    public ResponseEntity<List<Municipality>> getMuns() {
        List<Municipality> muns = this.aemetService.getMuns();

        if (!muns.isEmpty()) {
            return ResponseEntity.ok(muns);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/mun/{id}")
    public ResponseEntity<Municipality> getMunicipioPorId(@PathVariable String id) {
        Municipality mun = this.aemetService.getMunById(id);

        if (mun != null) {
            return ResponseEntity.ok(mun);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/mun/prediction/tomorrow/{id}")
    public ResponseEntity<ReportDTO> getPredictMunTomorrow(@PathVariable String id) {
        ReportDTO reportDTO = this.aemetService.getPredictMunTomorrow(id);

        if (reportDTO != null) {
            return ResponseEntity.ok(reportDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/mun/conversion/{avg}/{unit}")
    public ResponseEntity<TemperatureDTO> getConversion(@PathVariable float avg, @PathVariable String unit) {
        TemperatureDTO temperatureDTO = this.aemetService.getConversion(avg, unit);

        if (temperatureDTO != null) {
            return ResponseEntity.ok(temperatureDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
