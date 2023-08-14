package com.springbootapp.weatherapp.model.collection;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Set;

@Document(collection = "forecast")
@Data
public class ForecastDocument {
    @Id
    private String id;
    private Date date;
    private String municipality;
    private TemperatureDocument temperature;
    private Set<ProbPrecipitationDocument> probPrecipitations;
}
