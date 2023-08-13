package com.springbootapp.weatherapp.repository;

import com.springbootapp.weatherapp.model.entity.ForecastEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("prod")
public interface ForecastRepository extends JpaRepository<ForecastEntity, Long> {

    @Query("SELECT forecast FROM ForecastEntity forecast WHERE forecast.municipality = ?1 order by forecast.id")
    List<ForecastEntity> findByMunicipality(String name);
}
