package com.springbootapp.weatherapp.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "municipality")
public class MunicipalityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String name;

    public MunicipalityEntity(){}

    public MunicipalityEntity(String municipality) {
        this.name = municipality;
    }
}
