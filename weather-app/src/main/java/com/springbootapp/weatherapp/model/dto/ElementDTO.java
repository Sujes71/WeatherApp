package com.springbootapp.weatherapp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class ElementDTO {
    Long id;
    String municipality;
    Date date;
    String unit;
    Float avg;
    Float precipitation_avg;

    public ElementDTO() {

    }
}
