package com.springbootapp.weatherapp.service;
import com.springbootapp.weatherapp.model.Municipality;
import com.springbootapp.weatherapp.model.dto.ForecastDTO;
import com.springbootapp.weatherapp.model.dto.TemperatureDTO;

import java.util.List;

public interface AemetService {

    List<Municipality> getMunicipalities();
    Municipality getMunById(String id);
    ForecastDTO getMunTomorrowForecast(String id);
    TemperatureDTO getConversion(Float avg, String unit);
}
