package de.collectioncompanion.SteamWebCrawler.adapter;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/collection")
public class RestServerIn {

    /**
     * Search for collection and send to Composer
     *
     * @param id The id of the collection
     * @param category The category of the collection
     * @param searchTerm The term to search for in the category
     * @return Return the response as string
     */
    @PostMapping
    public ResponseEntity<String> receiveCollection(@RequestParam long id, @RequestParam String category,
                                                    @RequestParam String searchTerm) {
        System.out.println("Collection request was sent successfully!");
        return ResponseEntity.status(200).body("Collection request was sent successfully!");
    }

}

