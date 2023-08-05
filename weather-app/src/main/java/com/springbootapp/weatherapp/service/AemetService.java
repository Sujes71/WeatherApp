package com.springbootapp.weatherapp.service;
import com.springbootapp.weatherapp.model.Municipality;
import com.springbootapp.weatherapp.model.dto.ReportDTO;
import com.springbootapp.weatherapp.model.dto.TemperatureDTO;

import java.util.List;

public interface AemetService {

    List<Municipality> getMuns();
    Municipality getMunById(String id);
    ReportDTO getPredictMunTomorrow(String id);
    TemperatureDTO getConversion(float avg, String unit);
}
