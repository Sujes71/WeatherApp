package com.springbootapp.weatherapp.repository;

import com.springbootapp.weatherapp.model.entity.MunicipalityEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProbPrecipitationRepository extends JpaRepository<MunicipalityEntity, Long> {
}
