package de.collectioncompanion.ResultsMS.ports.outbound;

import de.collectioncompanion.ResultsMS.ports.data_files.Collection;
import org.springframework.http.ResponseEntity;

public interface RestOut {

    /**
     * Do a POST-REST-request to a microservice/passed to find requested collection
     *
     * @param uriToDBMicroService URI of microservice (http://<hostname>:<port>/collection)
     * @param collection Pass a request to search a collection in the internet
     * @return Returns the response as String
     */
    ResponseEntity<String> doPostCollection(String uriToDBMicroService, Collection collection);

}
