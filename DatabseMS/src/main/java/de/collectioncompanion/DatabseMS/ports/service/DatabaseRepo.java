package de.collectioncompanion.DatabseMS.ports.service;

import org.springframework.data.mongodb.repository.MongoRepository;
import ports.Collection;

public interface DatabaseRepo extends MongoRepository<Collection, String> {

    //List<Collection> findByCategory(String category);

}
