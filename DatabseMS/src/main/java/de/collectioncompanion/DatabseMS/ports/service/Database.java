package de.collectioncompanion.DatabseMS.ports.service;

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
}
