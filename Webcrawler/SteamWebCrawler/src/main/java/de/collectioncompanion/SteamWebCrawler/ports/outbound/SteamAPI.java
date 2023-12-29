package de.collectioncompanion.SteamWebCrawler.ports.outbound;

import de.collectioncompanion.SteamWebCrawler.ports.data_files.Collection;
import de.collectioncompanion.SteamWebCrawler.ports.data_files.WebCrawler;

import java.util.TreeMap;

/**
 * Can only look for games -> category = games
 */
public interface SteamAPI {

    /**
     * Request all available games on steam as "app-ID"-"game name" pair
     *
     * @return A TreeMap of all "app-ID"-"game name" pairs
     */
    TreeMap<Integer, String> getAllGames();

    /**
     * Searches for a collection with passed search term -> Steam-API call
     *
     * @param searchTerm The search term to look for a collection
     * @return The requested collection
     */
    Collection findInformationToCollection(String searchTerm);

}
