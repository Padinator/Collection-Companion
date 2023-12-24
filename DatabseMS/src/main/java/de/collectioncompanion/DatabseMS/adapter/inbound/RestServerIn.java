package de.collectioncompanion.DatabseMS.adapter.inbound;

import de.collectioncompanion.DatabseMS.adapter.outbound.DatabaseOut;
import de.collectioncompanion.DatabseMS.ports.data_files.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/collection")
public class RestServerIn {

    @Autowired
    private DatabaseOut databaseOut;

    /**
     * Accepts get requests with exactly two parameters named like parameters below
     *
     * @param category The category of the collection
     * @param searchTerm The search term of the request
     * @return Return a response as response-object
     */
    @GetMapping
    ResponseEntity<String> getCollection(@RequestParam String category, @RequestParam String searchTerm) {
        Collection result = databaseOut.requestCollectionFromDB(category, searchTerm);
        System.out.println(result);

        if (!result.isEmpty() && result.isValid()) // Found a valid collection
            return ResponseEntity.status(200).body(result.toString());
        else // Found no collection or an outdated collection
            return ResponseEntity.status(204).body(result.toString());
    }

}
