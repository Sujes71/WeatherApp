package com.springbootapp.weatherapp.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "temperature")
public class TemperatureEntity {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private Float avg;
    @Column
    private String unit;

    public TemperatureEntity(){}

}
