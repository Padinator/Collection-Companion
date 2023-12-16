package de.collectioncompanion.DatabseMS.adapter.inbound;

import de.collectioncompanion.DatabseMS.adapter.outbound.DatabaseOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        System.out.println(456);
        return ResponseEntity.status(200).body(databaseOut.requestCollectionFromDB(category, searchTerm).toString());
    }

}
