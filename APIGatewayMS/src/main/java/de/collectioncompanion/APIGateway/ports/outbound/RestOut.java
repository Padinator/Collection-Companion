package de.collectioncompanion.APIGateway.ports.outbound;

import org.springframework.http.ResponseEntity;

import java.net.URI;

public interface RestOut {

    /**
     * Do a get request with the passed URI
     *
     * @param uri Pass an URI
     * @return Returns the response as String
     */
    ResponseEntity<String> doGetRequest(String uri);

}
