package com.springbootapp.weatherapp.model.collection;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "prob_precipitation")
@Data
public class ProbPrecipitationDocument {
    private Integer value;
    private String period;

}
