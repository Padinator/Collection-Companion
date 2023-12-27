package de.collectioncompanion.SteamWebCrawler.adapter.inbound;


import de.collectioncompanion.SteamWebCrawler.adapter.outbound.SteamAPIOut;
import de.collectioncompanion.SteamWebCrawler.ports.data_files.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/collection")
public class RestServerIn {

    @Autowired
    private SteamAPIOut steamAPIOut;

    /*
     * TODO: Change call from Composer-Microservice
     *  - Parameter "category" was removed
     *  - PostMapping was replaced with a GetMapping
     *  - Format collection, so that a collection can be transmitted
     */
    /**
     * Search for collection and send to Composer
     *
     * @param id The id of the collection
     * @param searchTerm The term to search for in the category
     * @return Return the response as string
     */
    @GetMapping
    public ResponseEntity<String> receiveCollection(@RequestParam long id, @RequestParam String searchTerm) {
        System.out.println("Collection request was sent successfully!");
        Collection collection = steamAPIOut.findInformationToCollection(searchTerm);
        System.out.println(collection);
        // return ResponseEntity.status(200).body(collection.toString());
        return ResponseEntity.status(200).body("Collection was created!");
    }

}

