package com.springbootapp.climaapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Prediccion {
    @JsonProperty("dia")
    private List<Dia> dia;

    public List<Dia> getDia() {
        return dia;
    }

    public void setDia(List<Dia> dia) {
        this.dia = dia;
    }

    @Override
    public String toString() {
        return "Prediccion{" +
                "dia=" + dia +
                '}';
    }
}
