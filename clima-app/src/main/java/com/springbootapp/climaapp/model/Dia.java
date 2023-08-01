package com.springbootapp.climaapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class Dia {
    @JsonProperty("fecha")
    private Date fecha;
    @JsonProperty("temperatura")
    private Temperatura temperatura;
    @JsonProperty("probPrecipitacion")
    private List<ProbPrecipitacion> probPrecipitacion;


    public List<ProbPrecipitacion> getProbPrecipitacion() {
        return probPrecipitacion;
    }

    public void setProbPrecipitacion(List<ProbPrecipitacion> probPrecipitacion) {
        this.probPrecipitacion = probPrecipitacion;
    }

    public Temperatura getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Temperatura temperatura) {
        this.temperatura = temperatura;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Dia{" +
                "probPrecipitacion=" + probPrecipitacion +
                ", temperatura=" + temperatura +
                ", fecha=" + fecha +
                '}';
    }
}
