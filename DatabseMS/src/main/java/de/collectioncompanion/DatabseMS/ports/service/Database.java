package de.collectioncompanion.DatabseMS.ports.service;

import ports.Collection;

public interface Database {

    /**
     * Return requested results or an empty string, if data must be renewed
     *
     * @param category of requested data
     * @param searchTerm of requested data
     * @return result as String
     */
    Collection select(String category, String searchTerm, DatabaseRepo databaseRepo);

    void insertCollection(Collection collection, DatabaseRepo databaseRepo);

}
