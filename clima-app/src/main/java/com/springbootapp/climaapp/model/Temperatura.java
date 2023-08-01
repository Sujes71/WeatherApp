package com.springbootapp.climaapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Temperatura {

    @JsonProperty("maxima")
    private Double maxima;
    @JsonProperty("minima")
    private Double minima;

    public Double getMaxima() {
        return maxima;
    }

    public void setMaxima(Double maxima) {
        this.maxima = maxima;
    }

    public Double getMinima() {
        return minima;
    }

    public void setMinima(Double minima) {
        this.minima = minima;
    }

    @Override
    public String toString() {
        return "Temperatura{" +
                "maxima=" + maxima +
                ", minima=" + minima +
                '}';
    }
}
