package de.collectioncompanion.ComposerMS.service.outbound;

import data_files.CollectionRequest;
import de.collectioncompanion.ComposerMS.ports.outbound.RestOut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestOutImpl implements RestOut {

    @Override
    public ResponseEntity<String> doGetCollectionRequest(String uriToMicroService, CollectionRequest collectionRequest) {
        RestTemplate restTemplate = new RestTemplate();
        // String uriWIthParams = uriToMicroService + collectionRequest.searchTerm();
        String uriWIthParams = uriToMicroService + collectionRequest.toPath();

        System.out.println("URI to call post request: " + uriWIthParams);
        return restTemplate.postForEntity(uriWIthParams, null, String.class);
    }
}
