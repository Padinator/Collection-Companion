package de.collectioncompanion.APIGateway.service.outbound;

import de.collectioncompanion.APIGateway.ports.outbound.RestOut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestOutImpl implements RestOut {

    @Override
    public ResponseEntity<String> doGetRequest(String url, String params) {
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("URL to do get request: " + url + params);
        return restTemplate.getForEntity(url + params, String.class);
    }

    @Override
    public ResponseEntity<String> doPostRequest(String url, String params) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(url + params, null, String.class);
    }

}
