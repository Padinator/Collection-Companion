package de.collectioncompanion.SteamWebCrawler.ports.inbound;

import ports.Collection;

public interface RestOut {

    /**
     * Searches for the search term
     *
     * @param searchTerm The search term to create a collection
     * @return Returns the response as String
     */
    Collection crawl(String searchTerm);

    /**
     * Response composer microservice
     *
     * @param collection The found collection will be sent to the composer microservice
     */
    void response(long id, Collection collection);

}
