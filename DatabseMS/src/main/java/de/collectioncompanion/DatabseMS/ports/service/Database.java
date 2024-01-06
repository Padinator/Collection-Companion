package de.collectioncompanion.DatabseMS.ports.service;

import data_files.CollectionImpl;
import de.collectioncompanion.DatabseMS.data_files.User;
import ports.Collection;

public interface Database {

    /**
     * Return requested results or an empty string, if data must be renewed
     *
     * @param category of requested data
     * @param searchTerm of requested data
     * @param databaseRepo object for accessing DB
     * @return result as String
     */
    Collection select(String category, String searchTerm, DatabaseRepo databaseRepo);

    /**
     * Insert new Collection into DB
     * 
     * @param collection to Insert
     * @param databaseRepo object for accessing DB
     */
    void insertCollection(Collection collection, DatabaseRepo databaseRepo);

    /**
     * Find to passed username the user
     * 
     * @param username of User
     * @param userRepo object for accessing DB
     * @return null if no user was found else the user
     */
    User selectUser(String username, UserRepo userRepo);

    /**
     * Insert User into DB
     * 
     * @param user to Insert
     * @param userRepo object for accessing DB
     */
    void insertUser(User user, UserRepo userRepo);

    /**
     * Insert Collection into passed User
     * 
     * @param username to insert Collection into
     * @param collection to insert into User
     * @param userRepo object for accessing DB
     * @param databaseRepo object for accessing DB
     */
    void insertCollectionToUser(String username, CollectionImpl collection, UserRepo userRepo, DatabaseRepo databaseRepo);

    /**
     * Add Friend To User
     * 
     * @param username to add passed Friend
     * @param usernameFriend to add in usernames list
     * @param userRepo object for accessing DB
     */
    void insertFriendToUser(String username, String usernameFriend, UserRepo userRepo);
}
