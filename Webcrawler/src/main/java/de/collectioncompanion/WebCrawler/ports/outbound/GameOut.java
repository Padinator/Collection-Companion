package de.collectioncompanion.WebCrawler.ports.outbound;

import ports.Collection;

import java.util.List;
import java.util.TreeMap;

/**
 * Can only look for games -> category = games
 */
public interface GameOut {

    /**
     * Request all available games on steam as "app-ID"-"game name" pair
     *
     * @return A TreeMap of all "app-ID"-"game name" pairs
     */
    TreeMap<Integer, String> getAllGames();

    /**
     * Searches for collections with passed search term -> Steam-API call
     *
     * @param searchTerm The search term to look for a collection
     * @return The requested collection
     */
    List<Collection> findCollections(String searchTerm);

}
