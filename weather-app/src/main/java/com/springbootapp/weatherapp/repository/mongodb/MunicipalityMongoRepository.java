package com.springbootapp.weatherapp.repository.mongodb;

import com.springbootapp.weatherapp.model.entity.MunicipalityDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MunicipalityMongoRepository extends MongoRepository<MunicipalityDocument, String> {
    @Query("{'municipality': ?0}")
    List<MunicipalityDocument> findByMunicipality(String municipality);
}

