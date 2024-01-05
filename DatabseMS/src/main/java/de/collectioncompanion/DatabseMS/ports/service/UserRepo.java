package de.collectioncompanion.DatabseMS.ports.service;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.collectioncompanion.DatabseMS.data_files.User;

public interface UserRepo extends MongoRepository<User, String> {

}
