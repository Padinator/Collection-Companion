package de.collectioncompanion.ComposerMS.ports.outbound;

import de.collectioncompanion.ComposerMS.data_files.CollectionRequest;
import org.springframework.http.ResponseEntity;

public interface RestOut {

    /**
     * Do a GET-REST-request to a microservice/passed to find requested collection
     *
     * @param uriToMicroService URI of microservice (http://<hostname>:<port>/collection)
     * @param collectionRequest Pass a request to search a collection in the internet
     * @return Returns the response as String
     */
    ResponseEntity<String> doGetCollectionRequest(String uriToMicroService, CollectionRequest collectionRequest);

}
