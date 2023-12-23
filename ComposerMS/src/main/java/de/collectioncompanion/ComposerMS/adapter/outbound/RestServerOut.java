package de.collectioncompanion.ComposerMS.adapter.outbound;

import de.collectioncompanion.ComposerMS.data_files.CollectionImpl;
import de.collectioncompanion.ComposerMS.data_files.CollectionRequest;
import de.collectioncompanion.ComposerMS.ports.data_files.Collection;
import de.collectioncompanion.ComposerMS.ports.outbound.RestOut;
import de.collectioncompanion.ComposerMS.ports.outbound.UpdatesNotificationPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.TreeMap;

public class RestServerOut {

    @Autowired
    private Environment environment;

    @Autowired
    private RestOut restOut;

    @Autowired
    private UpdatesNotificationPort updatesNotificationPort;

    /**
     * Port, on which each microservice can be used (basic addressing)
     */
    public static final int ROUTING_PORT = 8080;

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
        final String STEAM_WEBCRWALER_MS = "http://" + environment.getProperty("STEAM_WEBCRWALER_MS") + ":"
                + ROUTING_PORT + STARTING;
        ResponseEntity<String> responseCollection = restOut.doPostCollectionRequest(STEAM_WEBCRWALER_MS,
                collectionRequest);
        TreeMap<String, String> data = new TreeMap<>();
        data.put("steam_webcrawler", responseCollection.toString());
        Collection collection = new CollectionImpl(data); // Must create the collection out of the response
        updatesNotificationPort.notifyUpdate(collectionRequest.id(), collection);
    }

}
