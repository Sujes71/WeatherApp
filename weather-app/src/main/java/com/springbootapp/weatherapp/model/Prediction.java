package com.springbootapp.weatherapp.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

@Data
public class Prediction {
    @JsonAlias("dia")
    private List<DayData> day;
}
