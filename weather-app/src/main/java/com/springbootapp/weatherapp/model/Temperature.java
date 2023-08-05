package com.springbootapp.weatherapp.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Temperature {

    @JsonAlias("maxima")
    private Double max;
    @JsonAlias("minima")
    private Double min;
}
