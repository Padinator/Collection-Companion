package de.collectioncompanion.APIGateway.adapter.outbound;

import de.collectioncompanion.APIGateway.ports.outbound.RestOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RestServerOut {

    @Autowired
    private RestOut restOut;

    public ResponseEntity<String> doGetRequest(String uri) {
        return restOut.doGetRequest(uri);
    }

}
