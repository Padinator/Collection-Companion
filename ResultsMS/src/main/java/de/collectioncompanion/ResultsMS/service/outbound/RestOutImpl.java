package de.collectioncompanion.ResultsMS.service.outbound;

import de.collectioncompanion.ResultsMS.ports.data_files.Collection;
import de.collectioncompanion.ResultsMS.ports.outbound.RestOut;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestOutImpl implements RestOut {

    @Override
    public ResponseEntity<String> doPostCollection(String uriToDBMicroService, Collection collection) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Collection> request = new HttpEntity<>(collection, headers);

        System.out.println("URI to call post request: " + uriToDBMicroService);
        return restTemplate.postForEntity(uriToDBMicroService, request, String.class);
    }

}
