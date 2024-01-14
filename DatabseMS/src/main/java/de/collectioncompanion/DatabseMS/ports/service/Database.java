package de.collectioncompanion.DatabseMS.ports.service;

import data_files.CollectionImpl;
import de.collectioncompanion.DatabseMS.data_files.User;
import ports.Collection;

import java.util.List;

public interface Database {

    /*
     * "Search result"-requests only for table "search results = collection"
     */
    /**
     * Returns requested results or an empty string, if data must be renewed
     *
     * @param category of requested data
     * @param searchTerm of requested data
     * @param collectionRepo object for accessing DB
     * @return results as List of Collection's
     */
    List<Collection> selectCollections(String category, String searchTerm, CollectionRepo collectionRepo);

    /**
     * Inserts new Collection into DB
     * 
     * @param collection to Insert
     * @param collectionRepo object for accessing DB
     * @return true if collection could be inserted else false
     */
    boolean insertCollection(Collection collection, CollectionRepo collectionRepo);

    /*
     * "User"-requests
     */
    /**
     * Finds to passed username the user
     * 
     * @param username of User
     * @param userRepo object for accessing DB
     * @return null if no user was found else the user
     */
    User selectUser(String username, UserRepo userRepo);

    /**
     * Inserts User into DB
     * 
     * @param user to Insert
     * @param userRepo object for accessing DB
     * @return inserted User or null if User already exists
     */
    User insertUser(User user, UserRepo userRepo);

    /**
     * Updates User with passed parameters
     *
     * @param oldUsername of user to change data
     * @param newUsername of User
     * @param newPassword of User
     * @param newEmail of User
     * @return updated User object
     */
    User updateUser(String oldUsername, String newUsername, String newPassword, String newEmail, UserRepo userRepo);

    /*
     * "Sammlung" requests
     */
    /**
     * Adds new Sammlung To User
     * @param username  to insert sammlung
     * @param name  of sammlung
     * @param visibility of sammlung
     * @param category of sammlung
     * @param userRepo object for accessing DB
     * @return true if sammlung could be added to user else false
     */
    boolean insertSammlungToUser(String username, String name, String visibility, String category, UserRepo userRepo);

    /**
     * Updates a Sammlung with the number sammlungNummer in a users list of Sammlungen with passed Collection
     *
     * @param username name of User
     * @param sammlungNummer number of Sammlung in DB
     * @param newVisibility of Sammlung
     * @param userRepo object for accessing DB
     * @return true, if updating list of Sammlungen was done successfully
     */
    boolean updateSammlungOfUser(String username, int sammlungNummer, String newVisibility, UserRepo userRepo);

    /*
     * "User friends"-requests
     */
    /**
     * Adds Friend To User
     *
     * @param username to add passed Friend into
     * @param usernameFriend to add in usernames list
     * @param userRepo object for accessing DB
     * @return true if friend could be added to user else false
     */
    boolean insertFriendToUser(String username, String usernameFriend, UserRepo userRepo);

    /*
     * "Collection/Search result"-requests in table "User"
     */
    /**
     * Inserts Collection into passed User
     *
     * @param username to insert Collection into
     * @param collectionID to insert into User
     * @param userRepo object for accessing DB
     * @param collectionRepo object for accessing DB
     * @return true if collection could be inserted to user else false
     */
    boolean insertCollectionToUser(String username, int sammlungNummer, String collectionID, UserRepo userRepo, CollectionRepo collectionRepo);

}
