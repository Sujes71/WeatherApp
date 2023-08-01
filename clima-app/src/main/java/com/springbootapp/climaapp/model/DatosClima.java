package com.springbootapp.climaapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DatosClima {
    @JsonProperty("datos")
    private String datos;

    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

    @Override
    public String toString() {
        return "DatosClima{" +
                "datos='" + datos + '\'' +
                '}';
    }
}
