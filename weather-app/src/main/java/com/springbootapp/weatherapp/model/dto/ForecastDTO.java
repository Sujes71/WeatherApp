package com.springbootapp.weatherapp.model.dto;

import com.springbootapp.weatherapp.model.ProbPrecipitation;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ForecastDTO {

    private String name;
    private Date date;
    private Double avg;
    private String unit;
    private List<ProbPrecipitation> probPrecipitations;

    public ForecastDTO(){
        this.unit = "G_CEL";
    }
}
