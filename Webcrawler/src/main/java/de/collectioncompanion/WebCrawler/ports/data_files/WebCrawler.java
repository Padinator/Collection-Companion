package de.collectioncompanion.WebCrawler.ports.data_files;

import ports.Collection;

public interface WebCrawler {

    /**
     * Searches for a collection with passed search term -> API call, HTML checking, ...
     *
     * @param searchTerm The search term to look for a collection
     * @return The requested collection
     */
    Collection findInformationToCollection(String searchTerm);

}
