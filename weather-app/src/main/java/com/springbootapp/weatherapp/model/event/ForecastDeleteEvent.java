package com.springbootapp.weatherapp.model.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ForecastDeleteEvent {
    private Long id;
}
