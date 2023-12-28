package de.collectioncompanion.APIGateway.ports.outbound;

import org.springframework.http.ResponseEntity;

import java.net.URI;

public interface RestOut {

    /**
     * Do a get request to another microservice
     *
     * @param url Pass URL of microservice
     * @param params Pass the parameters of the URI
     * @return Returns the response as String
     */
    ResponseEntity<String> doGetRequest(String url, String params);

    /**
     * Do a post request to another microservice
     *
     * @param url Pass URL of microservice
     * @param params Pass the parameters of the URI
     * @return Returns the response as String
     */
    ResponseEntity<String> doPostRequest(String url, String params);

}
