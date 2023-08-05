package com.springbootapp.weatherapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DayData {
    @JsonProperty("fecha")
    private Date date;
    @JsonProperty("temperatura")
    private Temperature temperature;
    @JsonProperty("probPrecipitacion")
    private List<ProbPrecipitation> probPrecipitations;
}
