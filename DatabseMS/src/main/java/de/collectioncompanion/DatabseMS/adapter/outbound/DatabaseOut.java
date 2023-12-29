package de.collectioncompanion.DatabseMS.adapter.outbound;

import de.collectioncompanion.DatabseMS.ports.data_files.Collection;
import de.collectioncompanion.DatabseMS.ports.service.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

@Service
public class DatabaseOut {

    @Autowired
    private Database database;

    public Collection requestCollectionFromDB(String category, String searchTerm) {
        return database.select(category, searchTerm); // Query DB
    }

    public void insertCollection(Collection collection) {
        database.insertCollection(collection);
    }

}
