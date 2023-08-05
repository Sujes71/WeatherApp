package com.springbootapp.weatherapp.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Municipality implements Serializable {
    @JsonAlias("nombre")
    private String name;
    @JsonAlias("id")
    private String id;
}