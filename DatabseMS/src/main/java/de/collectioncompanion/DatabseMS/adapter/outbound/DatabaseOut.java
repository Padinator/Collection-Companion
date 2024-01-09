package de.collectioncompanion.DatabseMS.adapter.outbound;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data_files.CollectionImpl;
import de.collectioncompanion.DatabseMS.data_files.User;
import de.collectioncompanion.DatabseMS.ports.service.Database;
import de.collectioncompanion.DatabseMS.ports.service.CollectionRepo;
import de.collectioncompanion.DatabseMS.ports.service.UserRepo;
import ports.Collection;

import java.util.List;

@Service
public class DatabaseOut {

    @Autowired
    private Database database;

    @Autowired
    public CollectionRepo collectionRepo;

    @Autowired
    public UserRepo userRepo;

    public List<Collection> requestCollectionsFromDB(String category, String searchTerm) {
        return database.selectCollections(category, searchTerm, collectionRepo); // Query DB
    }

    public boolean addCollection(Collection collection) {
        return database.insertCollection(collection, collectionRepo);
    }

    public User addUser(User user) {
        return database.insertUser(user, userRepo);
    }

    public User requestUserFromDB(String username) {
        return database.selectUser(username, userRepo);
    }

    public boolean addCollectionToUsersSammlung(String username, int sammlungNummer, CollectionImpl collection) {
        return database.insertCollectionToUser(username, sammlungNummer, collection, userRepo, collectionRepo);
    }

    public boolean addFriendToUser(String username, String usernameFriend) {
        return database.insertFriendToUser(username, usernameFriend, userRepo);
    }

    public boolean addSammlungToUser(String username, String name, String visibility, String category) {
        return database.insertSammlungToUser(username, name, visibility, category, userRepo);
    }

    public boolean updateSammlungOfUser(String username, int sammlungNummer, String newVisibility) {
        return database.updateCollectionToUsersSammlung(username, sammlungNummer, newVisibility, userRepo);
    }
}
