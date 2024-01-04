package de.collectioncompanion.ComposerMS.adapter.outbound;

import data_files.CollectionRequest;
import de.collectioncompanion.ComposerMS.data_files.WebCrawlerImpl;
import de.collectioncompanion.ComposerMS.ports.data_files.WebCrawler;
import de.collectioncompanion.ComposerMS.ports.outbound.RestOut;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.http.ResponseEntity;

import java.util.LinkedList;
import java.util.List;

public class ControlDockerContainers {

    /**
     * Represents the maximum value for the relationship between the number of pending requests and the number of web
     * crawlers
     */
    private static final int MAX_WORK_LOAD_PER_MICROSERVICE = 10;

    /**
     * Save all active/running web crawlers. Start with one web crawler, initialized by "docker-compose ..."
     */
    private static final LinkedList<WebCrawler> activeWebCrawlers = new LinkedList<>(List.of(
           new WebCrawlerImpl("webcrawler-1", "webcrawler-1")
            //new WebCrawlerImpl("webcrawler-2", "webcrawler-2")
    ));

    private static int nextFreeNumber = 1;

    private static final Environment environment = new StandardEnvironment();

    public static void doRequest(RestOut restOut, CollectionRequest collectionRequest) {
        Pair<Integer, WebCrawler> result = measureWorkLoad();
        int workload = result.getLeft();
        WebCrawler webCrawlerWithLeastWorkload = result.getRight();
        System.out.println("Result: " + result);

        // If workload is too big or no web crawler is running, start another web crawler microservice
        if (activeWebCrawlers.size() == 0 || workload / activeWebCrawlers.size() >= MAX_WORK_LOAD_PER_MICROSERVICE) {
            webCrawlerWithLeastWorkload = startNewWebCrawler();
            System.out.println("created new web crawler");

            // Wait 10 seconds for new web crawler to start
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            actualizeWebCrawlers(workload);
            System.out.println("Did not create any web crawler");
        }

        // Request the web crawler with the least workload
        System.out.println("Active web crawlers: " + activeWebCrawlers);
        System.out.println("Chosen webcrawler: " + webCrawlerWithLeastWorkload);
        requestWebCrawler(webCrawlerWithLeastWorkload, restOut, collectionRequest);
    }

    /**
     * Calculates the workload per microservice: sum_of_all_pending_requests / sum_of_all_web_crawlers
     *
     * @return The result of the formula frm above
     */
    private static Pair<Integer, WebCrawler> measureWorkLoad() {
        Pair<WebCrawler, Integer> webCrawlerWithLeastWorkload = new ImmutablePair<>(null, -1);
        int workload = 0;

        for (WebCrawler webCrawler : activeWebCrawlers) { // Sequential -> better would be: parallel requesting
            int currentWorkload = webCrawler.getPendingRequests();
            workload += currentWorkload;

            if (webCrawlerWithLeastWorkload.getLeft() == null || currentWorkload < webCrawlerWithLeastWorkload.getRight())
                webCrawlerWithLeastWorkload = new ImmutablePair<>(webCrawler, currentWorkload);
        }

        return new ImmutablePair<>(workload, webCrawlerWithLeastWorkload.getLeft());
    }

    private static void requestWebCrawler(WebCrawler webCrawlerWithLeastWorkload, RestOut restOut,
                                          CollectionRequest collectionRequest) {
        //final String STEAM_WEBCRAWLER_MS = "http://localhost:8085/collection";
        ResponseEntity<String> response = restOut.doPostCollectionRequest(
                webCrawlerWithLeastWorkload.getUrlToWebCrawler(), collectionRequest);
        System.out.println("Response, active webcrawler-searches:" + response);
    }

    /**
     * Stop unused/less used web crawlers. Optimum per webcrawler is: MAX_WORK_LOAD_PER_MICROSERVICE / 2
     *
     * @param workload The workload of all web crawlers
     */
    private static void actualizeWebCrawlers(int workload) {
        int optimalCountOfWebCrawlers = workload == 0 ? 1 : workload / (MAX_WORK_LOAD_PER_MICROSERVICE / 2);
        int countOfWebCrawlersToStop = activeWebCrawlers.size() - optimalCountOfWebCrawlers;

        System.out.println("optimalCountOfWebCrawlers: " + optimalCountOfWebCrawlers);
        System.out.println("countOfWebCrawlersToStop: " + countOfWebCrawlersToStop);

        // Remove web crawlers from list, so they cannot be used for requests
        for (int i = 0; i < countOfWebCrawlersToStop; ++i)
            activeWebCrawlers.removeLast().stop();
    }

    /**
     * Creates a new web crawler out of the web crawler image
     *
     * @return Returns the created web crawler
     */
    private static WebCrawler startNewWebCrawler() {
        String hostname = "webcrawler-" + nextFreeNumber;
        String containerName = "webcrawler-" + nextFreeNumber++;
        String COMPOSER_MS = "COMPOSER_MS=" + environment.getProperty("COMPOSER_MS");
        String WEBCRAWLER_MS = "WEBCRAWLER_MS=" + environment.getProperty("WEBCRAWLER_MS");
        String STATISTICS_MS = "STATISTICS_MS=" + environment.getProperty("STATISTICS_MS");
        String runDockerContainer = "docker run -d --hostname " + hostname + " --name " + containerName
                + " --rm -e " + COMPOSER_MS + " -e " + WEBCRAWLER_MS + " -e " + STATISTICS_MS
                + " --network=collection-companion_default webcrawler";

        OPExecCmd.execCMD(runDockerContainer); // Start command with shell command
        activeWebCrawlers.add(new WebCrawlerImpl(hostname, containerName)); // Create new web crawler
        return activeWebCrawlers.getLast();
    }

}
