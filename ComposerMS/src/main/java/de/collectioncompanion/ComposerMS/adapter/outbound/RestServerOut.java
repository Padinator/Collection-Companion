package de.collectioncompanion.ComposerMS.adapter.outbound;

import de.collectioncompanion.ComposerMS.data_files.CollectionRequest;
import de.collectioncompanion.ComposerMS.ports.outbound.RestOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

public class RestServerOut {

    @Autowired
    private Environment environment;

    @Autowired
    private RestOut restOut;

    /**
     * Port, on which each microservice can be used (basic addressing)
     */
    public static final int ROUTING_PORT = 8080;

    /**
     * Starting of path of URI
     */
    public static final String STARTING = "/collection";


    @PostMapping
    public ResponseEntity<String> requestWebCrawler(CollectionRequest collectionRequest) {
        final String STEAM_WEBCRWALER_MS = "http://" + environment.getProperty("STEAM_WEBCRWALER_MS") + ":"
                + ROUTING_PORT + STARTING;
        return restOut.doPostCollectionRequest(STEAM_WEBCRWALER_MS, collectionRequest);
    }

}
