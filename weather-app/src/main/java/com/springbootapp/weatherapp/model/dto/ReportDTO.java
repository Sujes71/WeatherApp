package com.springbootapp.weatherapp.model.dto;

import com.springbootapp.weatherapp.model.ProbPrecipitation;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ReportDTO {

    private String name;
    private Date date;
    private Double temAvg;
    private String temUnit;
    private List<ProbPrecipitation> probPrecipitations;

    public ReportDTO (){
        this.temUnit = "G_CEL";
    }
}
