package com.springbootapp.weatherapp.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.io.Serializable;

@Data
public class Municipality implements Serializable {

    @JsonAlias("id")
    private String id;
    @JsonAlias("nombre")
    private String name;
}
