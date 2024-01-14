package de.collectioncompanion.DatabseMS.adapter.outbound;

import de.collectioncompanion.DatabseMS.data_files.User;
import de.collectioncompanion.DatabseMS.ports.service.CollectionRepo;
import de.collectioncompanion.DatabseMS.ports.service.Database;
import de.collectioncompanion.DatabseMS.ports.service.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    /*
     * "Search result"-requests only for table "search results = collection"
     */
    public List<Collection> requestCollectionsFromDB(String category, String searchTerm) {
        return database.selectCollections(category, searchTerm, collectionRepo); // Query DB
    }

    public boolean addCollection(Collection collection) {
        return database.insertCollection(collection, collectionRepo);
    }

    /*
     * "User"-requests
     */
    public User requestUserFromDB(String username) {
        return database.selectUser(username, userRepo);
    }

    public User addUser(User user) {
        return database.insertUser(user, userRepo);
    }

    public User updateUser(String oldUsername, String newUsername, String newPassword, String newEmail) {
        return database.updateUser(oldUsername, newUsername, newPassword, newEmail, userRepo);
    }

    public List<String> searchForUsers(String currentUser, String friendSearchTerm) {
        return database.getUsers(currentUser, friendSearchTerm, userRepo);
    }

    /*
     * "Sammlung" requests
     */
    public boolean addSammlungToUser(String username, String name, String visibility, String category) {
        return database.insertSammlungToUser(username, name, visibility, category, userRepo);
    }

    public boolean updateSammlungOfUser(String username, int sammlungNummer, String newVisibility) {
        return database.updateSammlungOfUser(username, sammlungNummer, newVisibility, userRepo);
    }

    /*
     * "User friends"-requests
     */
    public boolean addFriendToUser(String username, String usernameFriend) {
        return database.insertFriendToUser(username, usernameFriend, userRepo);
    }

    /*
     * "Collection/Search result"-requests in table "User"
     */
    public boolean addCollectionToUsersSammlung(String username, int sammlungNummer, String collectionID) {
        return database.insertCollectionToUser(username, sammlungNummer, collectionID, userRepo, collectionRepo);
    }

}
