package de.collectioncompanion.DatabseMS.ports.service;

import de.collectioncompanion.DatabseMS.ports.data_files.Collection;

public interface Database {

    public Collection select(String category, String searchTerm);

}
