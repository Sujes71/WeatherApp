package com.springbootapp.weatherapp.model.collection;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "temperature")
@Data
public class TemperatureDocument {
    private Float avg;
    private String unit;

}
