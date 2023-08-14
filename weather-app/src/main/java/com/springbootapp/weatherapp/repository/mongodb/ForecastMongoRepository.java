package com.springbootapp.weatherapp.repository.mongodb;

import com.springbootapp.weatherapp.model.collection.ForecastDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForecastMongoRepository extends MongoRepository<ForecastDocument, String> {
    @Query("{'municipality': ?0}")
    List<ForecastDocument> findByMunicipality(String municipality);
}
