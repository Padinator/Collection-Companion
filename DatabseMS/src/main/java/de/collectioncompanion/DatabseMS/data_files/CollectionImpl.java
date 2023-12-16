package de.collectioncompanion.DatabseMS.data_files;

import de.collectioncompanion.DatabseMS.ports.Collection;

import java.util.Map;

public class CollectionImpl implements Collection {

    private final Map<String, String> data;

    public CollectionImpl(Map<String, String> results) {
        data = results;
    }

    @Override
    public String toString() {
        return data.isEmpty() ? "" : data.toString();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }
}
