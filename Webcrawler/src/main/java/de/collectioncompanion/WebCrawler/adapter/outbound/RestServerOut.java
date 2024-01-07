package de.collectioncompanion.WebCrawler.adapter.outbound;

import de.collectioncompanion.WebCrawler.ports.inbound.RestOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import ports.Collection;

import java.util.List;

@Service
public class RestServerOut {

    @Autowired
    private RestOut restOut;

    /**
     * Response composer microservice
     *
     * @param id The ID of the collection request
     * @param collections The found collection, which will be sent to composer microservice
     */
    @PostMapping
    public void postResponse(long id, List<Collection> collections) {
        restOut.response(id, collections); // Return collection as post request to composer microservice
    }

}
