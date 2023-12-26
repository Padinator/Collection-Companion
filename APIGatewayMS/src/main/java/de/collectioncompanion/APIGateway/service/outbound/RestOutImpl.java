package de.collectioncompanion.APIGateway.service.outbound;

import de.collectioncompanion.APIGateway.ports.outbound.RestOut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class RestOutImpl implements RestOut {

    @Override
    public ResponseEntity<String> doGetRequest(String url, String params) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            System.out.println("URL to do get request: " + url + params);
            return restTemplate.getForEntity(new URI(url + params), String.class);
        } catch (URISyntaxException e) { // Can not create URI
            return ResponseEntity.status(503).body("Cannot create URI out of passed\nURL: " + url + "\nPramameters: "
                    + params);
        }
    }

    @Override
    public ResponseEntity<String> doPostRequest(String url, String params) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            return restTemplate.postForEntity(new URI(url + params), null, String.class);
        } catch (URISyntaxException e) { // Can not create URI
            return ResponseEntity.status(503).body("Cannot create URI out of passed\nURL: " + url + "\nPramameters: "
                    + params);
        }    }

}
