package com.springbootapp.weatherapp.repository.postgresql;

import com.springbootapp.weatherapp.model.entity.MunicipalityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProbPrecipitationRepository extends JpaRepository<MunicipalityEntity, Long> {
}
