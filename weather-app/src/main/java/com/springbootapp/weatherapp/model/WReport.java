package com.springbootapp.weatherapp.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class WReport {
    @JsonAlias("prediccion")
    private Prediction prediction;

}
