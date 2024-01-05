package de.collectioncompanion.DatabseMS.service;

import data_files.CollectionImpl;
import de.collectioncompanion.DatabseMS.ports.service.Database;
import de.collectioncompanion.DatabseMS.ports.service.DatabaseRepo;
import org.springframework.stereotype.Service;
import ports.Collection;

import java.util.List;
import java.util.TreeMap;

import static ports.CollectionFormatter.compareGameNames;

@Service
public class DatabaseImpl implements Database {

    /**
     * Do a select query
     *
     * @param category   category of a collection
     * @param searchTerm term to look for
     * @return the found collection or an empty one
     */
    public Collection select(String category, String searchTerm, DatabaseRepo databaseRepo) {
        List<Collection> results = databaseRepo.findAll().stream() // Query DB
                .filter(collection -> collection.getValue("category").equals(category)
                        && compareGameNames(collection.getValue("title"), searchTerm))
                .toList();

        System.out.println("\n\nFound:");
        System.out.println(databaseRepo.findAll() + "\n");
        System.out.println(databaseRepo.findAll().stream() // Query DB
                .filter(collection -> collection.getValue("category").equals(category))
                .toList() + "\n");

        System.out.println(databaseRepo.findAll().stream() // Query DB
                .filter(collection -> compareGameNames(collection.getValue("title"), searchTerm))
                .toList() + "\n\n");

        if (results.size() == 0)
            return new CollectionImpl(new TreeMap<>());

        System.out.println("Return: " + results.get(0));
        return results.get(0);
    }

    @Override
    public void insertCollection(Collection collection, DatabaseRepo databaseRepo) {
        // Insert all data into DB
        //collection.putEntry("title", "Passengers Of Execution");
        //collection.putEntry("category", "game");
        //collection.putEntry("time_stamp", "1734567890109");
        System.out.println("Collection to insert: " + collection);
        Collection c = databaseRepo.insert(collection);
        System.out.println("Inserted: " + c);
        System.out.println("Complete content:\n" + databaseRepo.findAll());
    }

}
