package de.collectioncompanion.ResultsMS.service.outbound;

import de.collectioncompanion.ResultsMS.ports.data_files.Collection;
import de.collectioncompanion.ResultsMS.ports.outbound.RestOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

public class RestOutImpl implements RestOut {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ResponseEntity<String> doPostRequest(String uriToDBMicroService, Collection collection) {
        String uriWIthParams = uriToDBMicroService + collection.toParams();

        try {
            return restTemplate.getForEntity(new URI(uriWIthParams), String.class);
        } catch (URISyntaxException e) { // Can not create URI
            return ResponseEntity.status(503).body("Cannot create URI out of passed URI to DB-MS and collection:\n"
                    + "Passed URI to DB-MS" + uriToDBMicroService
                    + "\nPassed Collection" + collection
                    + "\nCreated URI for request: " + uriWIthParams);
        }
    }

}
