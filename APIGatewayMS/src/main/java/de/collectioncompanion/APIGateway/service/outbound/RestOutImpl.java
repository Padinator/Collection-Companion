package de.collectioncompanion.APIGateway.service.outbound;

import de.collectioncompanion.APIGateway.ports.outbound.RestOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class RestOutImpl implements RestOut {

    @Override
    public ResponseEntity<String> doGetRequest(String uri) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            return restTemplate.getForEntity(new URI(uri), String.class);
        } catch (URISyntaxException e) { // Can not create URI
            return ResponseEntity.status(503).body("Cannot create URI out of passed String: " + uri);
        }
    }

}
