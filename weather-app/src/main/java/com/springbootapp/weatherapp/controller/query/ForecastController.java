package com.springbootapp.weatherapp.controller.query;

import com.springbootapp.weatherapp.model.collection.ForecastDocument;
import com.springbootapp.weatherapp.model.dto.ElementDTO;
import com.springbootapp.weatherapp.service.ForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/forecast")
public class ForecastController {

    @Autowired
    ForecastService forecastService;
    @GetMapping("/all")
    public ResponseEntity<List<ElementDTO>> getForecasts() {
        List<ElementDTO> result = forecastService.getForecasts();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/municipality/{name}")
    public ResponseEntity<List<ForecastDocument>> getForecastsByMunicipality(@PathVariable String name) {
        List<ForecastDocument> result = this.forecastService.getForecastsByMunicipality(name);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
