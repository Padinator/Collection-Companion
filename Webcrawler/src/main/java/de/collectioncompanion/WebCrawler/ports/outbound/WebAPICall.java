package de.collectioncompanion.WebCrawler.ports.outbound;

import ports.Collection;

import java.util.List;

public interface WebAPICall {

    /**
     * Searches for collections with passed search term -> API call, HTML checking, ...
     *
     * @param searchTerm The search term to look for a collection
     * @return The requested collection
     */
    List<Collection> findCollections(String searchTerm);

}
