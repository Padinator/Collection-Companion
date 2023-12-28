package de.collectioncompanion.APIGateway.adapter.outbound;

import de.collectioncompanion.APIGateway.ports.outbound.RestOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RestServerOut {

    @Autowired
    private RestOut restOut;

    /**
     * Do a get request to another microservice
     *
     * @param url    Pass an URL of microservice
     * @param params Pass the parameters of the URI
     * @return Returns the response as String
     */
    public ResponseEntity<String> doGetRequest(String url, String params) {
        return restOut.doGetRequest(url, params);
    }

    /**
     * Do a post request to another microservice
     *
     * @param url Pass URL of microservice
     * @param params Pass the parameters of the URI
     * @return Returns the response as String
     */
    public ResponseEntity<String> doPostRequest(String url, String params) {
        return restOut.doPostRequest(url, params);
    }
}
