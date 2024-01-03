package de.collectioncompanion.ComposerMS.ports.data_files;

public interface WebCrawler {

    /**
     * Getter for property hostname
     *
     * @return The hostname as String
     */
    String getHostname();

    /**
     * Getter for property containerName
     *
     * @return The container name as String
     */
    String getContainerName();

    /**
     * Getter for property urlToWebCrawler
     *
     * @return The url to web crawler as String
     */
    String getUrlToWebCrawler();

    /**
     * Stops a web crawler, after it is done with all requests
     */
    void stop();

    /**
     * Aks a web crawler for its pending requests
     *
     * @return The count of pending requests of the web crawler
     */
    int getPendingRequests();

}
