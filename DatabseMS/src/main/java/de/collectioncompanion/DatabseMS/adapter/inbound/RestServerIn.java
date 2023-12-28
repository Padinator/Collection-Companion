package de.collectioncompanion.DatabseMS.adapter.inbound;

import de.collectioncompanion.DatabseMS.adapter.outbound.DatabaseOut;
import de.collectioncompanion.DatabseMS.ports.data_files.Collection;
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
    public ResponseEntity<String> getCollection(@RequestParam String category, @RequestParam String searchTerm) {
        Collection result = databaseOut.requestCollectionFromDB(category, searchTerm);
        System.out.println(result);

        if (!result.isEmpty() && result.isValid()) // Found a valid collection
            return ResponseEntity.status(200).body(result.toString());
        else // Found no collection or an outdated collection
            return ResponseEntity.status(204).body(result.toString());
    }

    /**
     * Inserts a passed collection into DB
     *
     * @param steam_webcrawler Test parameter
     * @return Returns the success/response as string
     */
    @PostMapping
    public ResponseEntity<String> addNewCollection(@RequestParam String steam_webcrawler) {
        System.out.println("Requested was done successfully: " + steam_webcrawler);
        return ResponseEntity.status(200).body("Inserted successfully params into DB!");
    }

}
