package com.springbootapp.weatherapp.model.dto;

import lombok.Data;

@Data
public class TemperatureDTO {

    private float avg;
    private String unit;
}
