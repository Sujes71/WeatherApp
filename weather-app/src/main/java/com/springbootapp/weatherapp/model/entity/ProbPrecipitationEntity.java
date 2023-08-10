package com.springbootapp.weatherapp.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.context.annotation.Profile;

@Entity
@Data
@Table(name = "CT_PROBPRECIPITATION")
@Profile("prod")
public class ProbPrecipitationEntity {
    @Id
    @GeneratedValue
    private String id;
    @Column(name = "avg")
    private Float avg;
    @Column(name = "unit")
    private String unit;

    public ProbPrecipitationEntity(){}
}
