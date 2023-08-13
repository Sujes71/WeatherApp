package com.springbootapp.weatherapp.service;

import com.springbootapp.weatherapp.model.dto.ElementDTO;
import com.springbootapp.weatherapp.model.entity.ForecastEntity;

import java.util.List;

public interface ForecastService {

    List<ElementDTO> getForecasts();
    List<ForecastEntity> getForecastsByMunicipality(String name);
    ForecastEntity getForecastById(Long id);
    ForecastEntity addForecast(ForecastEntity forecastEntity);
    void deleteById(Long id);

}
