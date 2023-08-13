package com.springbootapp.weatherapp.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TemperatureEnum {
    TEMPERATURE_CEL("G_CEL"),
    TEMPERATURE_FAH("G_FAH");

    private String unit;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
