package de.collectioncompanion.DatabseMS.service;

import data_files.CollectionImpl;
import de.collectioncompanion.DatabseMS.ports.service.Database;
import org.springframework.stereotype.Service;
import ports.Collection;

import java.util.Map;
import java.util.TreeMap;

import static de.collectioncompanion.DatabseMS.adapter.outbound.DatabaseOut.databaseRepo;

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
        TreeMap<String, String> data = new TreeMap<>();
        data.put("time_stamp", "1704292340644");
        data.put("title", "Passengers Of Execution");
        data.put("category", "game");

        collection = new CollectionImpl(data);
        System.out.println("Collection to insert: " + collection);

        // Insert all data into DB
        Collection c = databaseRepo.insert(collection);
        System.out.println("Inserted: " + c);
    }

}
