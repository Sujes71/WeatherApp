package com.springbootapp.climaapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class InformeClima {
    @JsonProperty("prediccion")
    private Prediccion prediccion;


    public Prediccion getPrediccion() {
        return prediccion;
    }

    public void setPrediccion(Prediccion prediccion) {
        this.prediccion = prediccion;
    }

    @Override
    public String toString() {
        return "InformeClima{" +
                " prediccion=" + prediccion +
                '}';
    }
}
