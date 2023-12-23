package de.collectioncompanion.ResultsMS.ports.data_files;

import java.util.Map;
import java.util.TreeMap;

/**
 * Collects collections, which were dequeued from rabbitmq
 */
public class CollectionList {

    /**
     * [<ID1, Collection1>, ...]
     */
    private final static Map<Long, Collection> collections = new TreeMap<>();

    /**
     * Pushes a collection into the list
     *
     * @param id The ID of the collection
     * @param collection The collection itself
     */
    public static synchronized void pushCollection(long id, Collection collection) {
        collections.put(id, collection);
    }

    /**
     * Returns the collection with the ID id, else null will be returned
     *
     * @param id ID of the collection
     * @return Returns null, if no collection with the ID id exists, else removes and returns the requested collection
     */
    public static synchronized Collection popCollection(long id) {
        if (hasCollectionWithID(id))
            return collections.remove(id);
        return null;
    }

    /**
     * Check, if a collection with an ID is in the queue
     *
     * @param id The ID of the collection
     * @return Returns true, if a collection with the ID id is in the list
     */
    public static synchronized boolean hasCollectionWithID(long id) {
        return collections.containsKey(id);
    }

}
