package com.springbootapp.weatherapp.model.entity;

import com.springbootapp.weatherapp.model.ProbPrecipitation;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Profile;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "CT_FORECAST")
@Profile("prod")
public class ForecastEntity {
    @Id
    @GeneratedValue
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "date")
    private Date date;
    @Column(name = "avg")
    private Double avg;
    @Column(name = "unit")
    private String unit;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "forecast_id")
    private List<ProbPrecipitationEntity> probPrecipitations;


    public ForecastEntity(){}
}
