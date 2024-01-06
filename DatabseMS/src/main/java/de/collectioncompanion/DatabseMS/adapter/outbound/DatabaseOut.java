package de.collectioncompanion.DatabseMS.adapter.outbound;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data_files.CollectionImpl;
import de.collectioncompanion.DatabseMS.data_files.User;
import de.collectioncompanion.DatabseMS.ports.service.Database;
import de.collectioncompanion.DatabseMS.ports.service.DatabaseRepo;
import de.collectioncompanion.DatabseMS.ports.service.UserRepo;
import ports.Collection;

@Service
public class DatabaseOut {

    @Autowired
    private Database database;

    @Autowired
    public DatabaseRepo databaseRepo;

    @Autowired
    public UserRepo userRepo;

    public Collection requestCollectionFromDB(String category, String searchTerm) {
        return database.select(category, searchTerm, databaseRepo); // Query DB
    }

    public void insertCollection(Collection collection) {
        database.insertCollection(collection, databaseRepo);
    }

    public void insertUser(User user) {
        database.insertUser(user, userRepo);
    }

    public User requestUserFromDB(String username) {
        return database.selectUser(username, userRepo);
    }

    public void insertCollectionToUser(String username, CollectionImpl collection) {
        database.insertCollectionToUser(username, collection);
    }
}
