package de.collectioncompanion.ComposerMS.service.outbound;

import de.collectioncompanion.ComposerMS.data_files.CollectionRequest;
import de.collectioncompanion.ComposerMS.ports.outbound.RestOut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class RestOutImpl implements RestOut {

    @Override
    public ResponseEntity<String> doPostCollectionRequest(String uriToMicroService, CollectionRequest collectionRequest) {
        RestTemplate restTemplate = new RestTemplate();
        String uriWIthParams = uriToMicroService + collectionRequest.toPath();

        try {
            return restTemplate.getForEntity(new URI(uriWIthParams), String.class);
        } catch (URISyntaxException e) { // Can not create URI
            return ResponseEntity.status(503).body("Cannot create URI out of passed URI and collection request:\n"
                    + "Passed URI to DB-MS" + uriToMicroService
                    + "\nPassed collection request: " + collectionRequest
                    + "\nCreated URI for request: " + uriWIthParams);
        }
    }
}
