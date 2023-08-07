package com.springbootapp.weatherapp.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DayData {
    @JsonAlias("fecha")
    private Date date;
    @JsonAlias("temperatura")
    private Temperature temperature;
    @JsonAlias("probPrecipitacion")
    private List<ProbPrecipitation> probPrecipitations;
}
