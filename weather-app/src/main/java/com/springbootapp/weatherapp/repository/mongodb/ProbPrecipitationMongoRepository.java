package com.springbootapp.weatherapp.repository.mongodb;

import com.springbootapp.weatherapp.model.collection.ProbPrecipitationDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProbPrecipitationMongoRepository extends MongoRepository<ProbPrecipitationDocument, String> {
}
