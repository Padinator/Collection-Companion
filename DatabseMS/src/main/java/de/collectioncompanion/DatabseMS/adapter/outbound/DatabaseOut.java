package de.collectioncompanion.DatabseMS.adapter.outbound;

import de.collectioncompanion.DatabseMS.ports.service.Database;
import de.collectioncompanion.DatabseMS.ports.service.DatabaseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ports.Collection;

@Service
public class DatabaseOut {

    @Autowired
    private Database database;

    @Autowired
    public static DatabaseRepo databaseRepo;

    public Collection requestCollectionFromDB(String category, String searchTerm) {
        return database.select(category, searchTerm); // Query DB
    }

    public void insertCollection(Collection collection) {
        database.insertCollection(collection);
    }

}
