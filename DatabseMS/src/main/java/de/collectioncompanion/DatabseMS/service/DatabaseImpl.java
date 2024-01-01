package de.collectioncompanion.DatabseMS.service;

import data_files.CollectionImpl;
import de.collectioncompanion.DatabseMS.ports.service.Database;
import org.springframework.stereotype.Service;
import ports.Collection;

import java.util.Map;
import java.util.TreeMap;

@Service
public class DatabaseImpl implements Database {

    /**
     * Do a select query
     *
     * @param category   category of a collection
     * @param searchTerm term to look for
     * @return the found collection or an empty one
     */
    public Collection select(String category, String searchTerm) {
        Map<String, String> results = new TreeMap<>();

        // Query DB
        results.put("time_stamp", "1693413832070"); // Invalid entry

        return new CollectionImpl(results);
    }

    @Override
    public void insertCollection(Collection collection) {
        collection.putEntry("time_stamp", String.valueOf(System.currentTimeMillis())); // First add a timestamp

        // Insert all data into DB
        // insert(data into DB ...);
    }

}
