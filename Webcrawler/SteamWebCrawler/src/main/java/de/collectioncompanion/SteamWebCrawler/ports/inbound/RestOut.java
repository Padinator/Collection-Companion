package de.collectioncompanion.SteamWebCrawler.ports.inbound;

import ports.Collection;

public interface RestOut {

    /**
     * Searches for the search term for creating a collection for games
     *
     * @param searchTerm The search term to create a collection
     * @return Returns the response as String
     */
    Collection crawlGame(String searchTerm);

    /**
     * Searches for the search term for creating a collection for a movie
     *
     * @param searchTerm The search term to create a collection
     * @return Returns the response as String
     */
    Collection crawlMovie(String searchTerm);

    /**
     * Searches for the search term for creating a collection for a series
     *
     * @param searchTerm The search term to create a collection
     * @return Returns the response as String
     */
    Collection crawlSeries(String searchTerm);

    /**
     * Searches for the search term for creating a collection for a comic
     *
     * @param searchTerm The search term to create a collection
     * @return Returns the response as String
     */
    Collection crawlComic(String searchTerm);

    /**
     * Response composer microservice
     *
     * @param collection The found collection will be sent to the composer microservice
     */
    void response(long id, Collection collection);

}
