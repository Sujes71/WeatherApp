package com.springbootapp.weatherapp.model.dto;

import lombok.Data;

@Data
public class TemperatureDTO {

    private Float avg;
    private String unit;
}
