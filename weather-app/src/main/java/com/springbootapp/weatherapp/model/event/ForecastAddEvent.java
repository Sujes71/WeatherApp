package com.springbootapp.weatherapp.model.event;

import com.springbootapp.weatherapp.model.entity.ForecastEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ForecastAddEvent {
    private ForecastEntity forecastEntity;

}
