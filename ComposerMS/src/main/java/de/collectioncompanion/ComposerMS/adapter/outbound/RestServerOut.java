package de.collectioncompanion.ComposerMS.adapter.outbound;

import data_files.CollectionRequest;
import de.collectioncompanion.ComposerMS.ports.outbound.RestOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Objects;

@Service
public class RestServerOut {

    @Autowired
    private Environment environment;

    @Autowired
    private RestOut restOut;

    /**
     * Starting of path of URI
     */
    public static final String STARTING = "/collection";

    /**
     * Requests a Webcrawler and pushes the response in the out rabbitmq
     *
     * @param collectionRequest Pass the collection request to work off
     */
    @PostMapping
    public void requestWebCrawler(CollectionRequest collectionRequest) {
        final String STEAM_WEBCRAWLER_MS = "http://" + environment.getProperty("STEAM_WEBCRAWLER_MS") + STARTING;
        //final String STEAM_WEBCRAWLER_MS = "http://localhost:8085/collection";
        ResponseEntity<String> response = restOut.doPostCollectionRequest(STEAM_WEBCRAWLER_MS,
                collectionRequest);
        int activeWebCrawlerSearches = Integer.parseInt(Objects.requireNonNull(response.getBody()));
        System.out.println("Response, active webcrawler-searches:" + activeWebCrawlerSearches);
    }

}
