package com.springbootapp.weatherapp.model.dto;

import com.springbootapp.weatherapp.model.Municipality;
import com.springbootapp.weatherapp.model.ProbPrecipitation;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ForecastDTO {

    private Municipality municipality;
    private Date date;
    private TemperatureDTO temperature;
    private List<ProbPrecipitation> probPrecipitations;

}
