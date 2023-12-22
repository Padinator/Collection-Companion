package de.collectioncompanion.DatabseMS.adapter.outbound;

import de.collectioncompanion.DatabseMS.ports.data_files.Collection;
import de.collectioncompanion.DatabseMS.ports.service.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseOut {

    @Autowired
    private Database database;

    /**
     * Return requested results or an empty string, if data must be renewed
     *
     * @param category of requested data
     * @param searchTerm of requested data
     * @return result as String
     */
    public Collection requestCollectionFromDB(String category, String searchTerm) {
        return database.select(category, searchTerm); // Query DB
    }

}
