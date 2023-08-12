package com.springbootapp.weatherapp.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "prob_precipitation")
public class ProbPrecipitationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private Integer value;
    @Column
    private String period;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forecast_id ")
    @JsonBackReference
    private ForecastEntity forecast;

    public ProbPrecipitationEntity(){}
}
