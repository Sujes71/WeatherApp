package com.springbootapp.climaapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProbPrecipitacion {

    @JsonProperty("value")
    private Integer value;
    @JsonProperty("periodo")
    private String periodo;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    @Override
    public String toString() {
        return "ProbPrecipitacion{" +
                "value=" + value +
                ", periodo='" + periodo + '\'' +
                '}';
    }
}
