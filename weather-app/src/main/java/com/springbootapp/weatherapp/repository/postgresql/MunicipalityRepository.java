package com.springbootapp.weatherapp.repository.postgresql;

import com.springbootapp.weatherapp.model.entity.MunicipalityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MunicipalityRepository extends JpaRepository<MunicipalityEntity, Long> {

    @Query("SELECT mun FROM MunicipalityEntity mun WHERE mun.name = ?1")
    MunicipalityEntity findByName(String name);
}
