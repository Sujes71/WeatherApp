package com.springbootapp.climaapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Municipio {
    @JsonProperty("nombre")
    private String nombre;
    @JsonProperty("id")
    private String id;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Municipio{" +
                "nombre='" + nombre + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
