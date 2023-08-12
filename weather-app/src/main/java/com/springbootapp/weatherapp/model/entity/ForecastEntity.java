package com.springbootapp.weatherapp.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Profile;

import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "forecast")
public class ForecastEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column
    private String municipality;
    @OneToOne(
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinColumn(name = "temperature_id")
    private TemperatureEntity temperature;
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "forecast")
    @JsonManagedReference
    private Set<ProbPrecipitationEntity> probPrecipitations;

    public ForecastEntity(){}
}

