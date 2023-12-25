package de.collectioncompanion.ResultsMS.adapter.outbound;

import de.collectioncompanion.ResultsMS.ports.data_files.Collection;
import de.collectioncompanion.ResultsMS.ports.outbound.RestOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DatabaseServerOut {

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

    public ResponseEntity<String> addResultingCollectionToDB(Collection collection) {
        final String DATABASE_MS = "http://" + environment.getProperty("DATABASE_MS") + ":" + ROUTING_PORT + STARTING;
        //final String DATABASE_MS = "http://localhost:8081/collection";
        return restOut.doPostCollection(DATABASE_MS, collection);
    }

}
