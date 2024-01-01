package de.collectioncompanion.SteamWebCrawler.adapter.outbound;

import de.collectioncompanion.SteamWebCrawler.ports.inbound.RestOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import ports.Collection;

@Service
public class RestServerOut {

    @Autowired
    private RestOut restOut;

    /**
     * Response composer microservice
     *
     * @param collection The found collection, which will be sent to composer microservice
     */
    @PostMapping
    public void postResponse(long id, Collection collection) {
        restOut.response(id, collection); // Return collection as post request to composer microservice
    }

}
