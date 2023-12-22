package de.collectioncompanion.ResultsMS.ports.outbound;

import de.collectioncompanion.ResultsMS.ports.data_files.Collection;
import org.springframework.http.ResponseEntity;

public interface RestOut {

    ResponseEntity<String> doPostRequest(String uriToDBMicroService, Collection collection);

}
