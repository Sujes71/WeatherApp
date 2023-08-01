package com.springbootapp.climaapp.model.dto;

import com.springbootapp.climaapp.model.ProbPrecipitacion;

import java.util.Date;
import java.util.List;

public class InformeDTO {

    private String nombre;
    private Date fecha;
    private Double mediaTemperatura;
    private String unidadTemperatura;
    private List<ProbPrecipitacion> probPrecipitacion;

    public InformeDTO (){
        this.unidadTemperatura = "G_CEL";
    }
    public Double getMediaTemperatura() {
        return mediaTemperatura;
    }

    public void setMediaTemperatura(Double mediaTemperatura) {
        this.mediaTemperatura = mediaTemperatura;
    }

    public String getUnidadTemperatura() {
        return unidadTemperatura;
    }

    public void setUnidadTemperatura(String unidadTemperatura) {
        this.unidadTemperatura = unidadTemperatura;
    }

    public List<ProbPrecipitacion> getProbPrecipitacion() {
        return probPrecipitacion;
    }

    public void setProbPrecipitacion(List<ProbPrecipitacion> probPrecipitacion) {
        this.probPrecipitacion = probPrecipitacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "InformeDTO{" +
                "nombre='" + nombre + '\'' +
                ", fecha=" + fecha +
                ", mediaTemperatura=" + mediaTemperatura +
                ", unidadTemperatura='" + unidadTemperatura + '\'' +
                ", probPrecipitacion=" + probPrecipitacion +
                '}';
    }
}
