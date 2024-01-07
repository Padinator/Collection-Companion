package de.collectioncompanion.WebCrawler.data_files;

import de.collectioncompanion.WebCrawler.adapter.outbound.RestServerOut;
import de.collectioncompanion.WebCrawler.ports.inbound.RestOut;
import ports.Collection;

import java.util.List;
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
            List<Collection> results;

            // Acquire all resources
            countOfWebcrawlers.acquire();
            raiseCounterForActiveWebcrawlerSearches();

            // Find collection and response composer microservice
            results = switch (category) {
                case "game" -> restOut.crawlGame(searchTerm); // Search in games
                case "movie" -> restOut.crawlMovie(searchTerm); // Search in movies
                case "series" -> restOut.crawlSeries(searchTerm); // Search in series
                default -> restOut.crawlComic(searchTerm); // Search in comics
            };

            // Add default values, which are always necessary
            for (Collection result : results) {
                result.putEntry("category", category); // Add the category of the collection to the collection
                result.putEntry("time_stamp", String.valueOf(System.currentTimeMillis())); // Add time stamp
                System.out.println("Created the collection: " + result);
            }

            // Response composer microservice
            restServerOut.postResponse(id, results);

            // Release all resources
            lowerCounterForActiveWebcrawlerSearches();
            countOfWebcrawlers.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
