package com.springbootapp.weatherapp.service;

import com.springbootapp.weatherapp.model.collection.ForecastDocument;
import com.springbootapp.weatherapp.model.dto.ElementDTO;
import com.springbootapp.weatherapp.model.entity.ForecastEntity;

import java.util.List;

public interface ForecastService {

    List<ElementDTO> getForecasts();
    List<ForecastDocument> getForecastsByMunicipality(String name);
    ForecastDocument getForecastById(Long id);
    boolean addForecast(ForecastEntity forecastEntity);
    boolean deleteById(Long id);

}
