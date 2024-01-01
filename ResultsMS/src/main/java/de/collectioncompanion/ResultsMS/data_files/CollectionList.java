package de.collectioncompanion.ResultsMS.data_files;


import ports.Collection;

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
     * Pushes synchronized a collection into the list
     *
     * @param id The ID of the collection
     * @param collection The collection itself
     */
    public static void pushCollection(long id, Collection collection) {
        synchronized (collections) {
            collections.put(id, collection);
            collections.notifyAll(); // Notify all threads that a new entry is available
        }
    }

    /**
     * Returns synchronized the collection with the ID id, else null will be returned
     *
     * @param id ID of the collection
     * @return Returns null, if no collection with the ID id exists, else removes and returns the requested collection
     */
    public static Collection popCollection(long id) {
        synchronized(collections) {
            while (!hasCollectionWithID(id))
                try {
                    System.out.println("Current collections: " + collections);
                    collections.wait(); // Wait, until the collection was enqueued
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            return collections.remove(id);
        }
    }

    /**
     * Check unsynchronized, if a collection with an ID is in the queue
     *
     * @param id The ID of the collection
     * @return Returns true, if a collection with the ID id is in the list
     */
    public static boolean hasCollectionWithID(long id) {
        return collections.containsKey(id);
    }

}
