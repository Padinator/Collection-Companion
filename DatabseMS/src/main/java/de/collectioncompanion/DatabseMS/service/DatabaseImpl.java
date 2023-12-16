package de.collectioncompanion.DatabseMS.service;

import de.collectioncompanion.DatabseMS.data_files.CollectionImpl;
import de.collectioncompanion.DatabseMS.ports.Collection;
import de.collectioncompanion.DatabseMS.ports.Database;
import org.springframework.stereotype.Service;

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
        results.put("test entry", "test123");

        return new CollectionImpl(results);
    }

}
