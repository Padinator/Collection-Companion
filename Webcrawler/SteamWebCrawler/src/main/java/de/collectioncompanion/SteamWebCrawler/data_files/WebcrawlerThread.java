package de.collectioncompanion.SteamWebCrawler.data_files;

import de.collectioncompanion.SteamWebCrawler.adapter.outbound.RestServerOut;
import de.collectioncompanion.SteamWebCrawler.ports.data_files.Collection;
import de.collectioncompanion.SteamWebCrawler.ports.inbound.RestOut;

import java.util.concurrent.Semaphore;

public class WebcrawlerThread extends Thread {

    private final String searchTerm, category;

    private final long id;

    /**
     * The maximum count of threads, which max search as webcrawlers on APIs, html documents etc.
     */
    private static final int MAX_COUNT_OF_ACTIVE_WEBCRAWLER_SEARCHES = 8;

    /**
     * Synchronize the current count of active webcrawler searches
     */
    private static final Semaphore countOfWebcrawlers = new Semaphore(MAX_COUNT_OF_ACTIVE_WEBCRAWLER_SEARCHES);

    /**
     * Count number of active searches for returning to caller
     */
    private static int counterForActiveWebcrawlerSearches = 0;

    /**
     * Mutex for variable counterForActiveWebcrawlerSearches
     */
    private static final Semaphore counterForActiveWebcrawlerSearchesMutex = new Semaphore(1);

    private final RestOut restOut;

    private final RestServerOut restServerOut;

    public WebcrawlerThread(String category, String searchTerm, long id, RestOut restOut, RestServerOut restServerOut) {
        this.category = category;
        this.searchTerm = searchTerm;
        this.restOut = restOut;
        this.restServerOut = restServerOut;
        this.id = id;
        start();
    }

    public static void raiseCounterForActiveWebcrawlerSearches() {
        try {
            counterForActiveWebcrawlerSearchesMutex.acquire();
            ++counterForActiveWebcrawlerSearches;
            counterForActiveWebcrawlerSearchesMutex.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void lowerCounterForActiveWebcrawlerSearches() {
        try {
            counterForActiveWebcrawlerSearchesMutex.acquire();
            --counterForActiveWebcrawlerSearches;
            counterForActiveWebcrawlerSearchesMutex.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getCounterForActiveWebcrawlerSearches() {
        int count = -1;

        try {
            counterForActiveWebcrawlerSearchesMutex.acquire();
            count = counterForActiveWebcrawlerSearches;
            counterForActiveWebcrawlerSearchesMutex.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return count;
    }

    @Override
    public void run() {
        try {
            // Acquire all resources
            countOfWebcrawlers.acquire();
            raiseCounterForActiveWebcrawlerSearches();

            // Find collection and response composer microservice
            Collection result = restOut.crawl(searchTerm); // Search and create collection in web
            result.putEntry("category", category); // Add the category of the collection to the collection
            result.putEntry("time_stamp", String.valueOf(System.currentTimeMillis())); // Add time stamp
            System.out.println("Created the collection: " + result);
            restServerOut.postResponse(id, result);

            // Release all resources
            lowerCounterForActiveWebcrawlerSearches();
            countOfWebcrawlers.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
