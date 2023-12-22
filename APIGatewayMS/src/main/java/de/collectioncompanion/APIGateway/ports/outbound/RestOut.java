package de.collectioncompanion.APIGateway.ports.outbound;

import org.springframework.http.ResponseEntity;

import java.net.URI;

public interface RestOut {

    ResponseEntity<String> doGetRequest(String uri);

}
