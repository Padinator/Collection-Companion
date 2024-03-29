package de.collectioncompanion.ResultsMS.adapter.outbound;

import de.collectioncompanion.ResultsMS.ports.outbound.RestOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ports.Collection;

import java.util.List;

@Service
public class DatabaseServerOut {

    @Autowired
    private Environment environment;

    @Autowired
    private RestOut restOut;

    /**
     * Port, on which each microservice can be used (basic addressing)
     */
    // public static final int ROUTING_PORT = 8080;

    /**
     * Starting of path of URI
     */
    public static final String STARTING = "/collection";

    public ResponseEntity<String> addResultingCollectionsToDB(List<Collection> collections) {
        final String DATABASE_MS = "http://" + environment.getProperty("DATABASE_MS") + STARTING;
        // final String DATABASE_MS = "http://localhost:8081/collection";

        for (Collection collection : collections) {
            String dbCollectionID = restOut.doPostCollection(DATABASE_MS, collection).getBody();
            collection.setId(dbCollectionID);
        }

        return ResponseEntity.status(200).body("Inserted " + collections.size() + " search results into DB!");
    }

}
