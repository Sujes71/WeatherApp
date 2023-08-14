package com.springbootapp.weatherapp.model.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "municipality")
@Data
public class MunicipalityDocument {
    private String id;
    private String name;

    // Getters y setters
    // Constructor(s) si es necesario
}
