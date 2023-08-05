package com.springbootapp.weatherapp.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProbPrecipitation {

    @JsonAlias("value")
    private Integer value;
    @JsonAlias("periodo")
    private String period;
}
