package de.collectioncompanion.ResultsMS.ports.data_files;

import java.util.Map;
import java.util.TreeMap;

public class CollectionQueue {

    private final static Map<Long, Collection> collections = new TreeMap<>();

    public static synchronized void enqueueCollection(long id, Collection collection) {
        collections.put(id, collection);
    }

    /**
     * Returns the collection with the ID id, else null will be returned
     *
     * @param id ID of the collection
     * @return Returns null, if no collection with the ID id exists, else removes and returns the requested collection
     */
    public static synchronized Collection dequeueCollection(long id) {
        if (hasCollectionWithID(id))
            return collections.remove(id);
        return null;
    }

    public static synchronized boolean hasCollectionWithID(long id) {
        return collections.containsKey(id);
    }

}
