package de.collectioncompanion.WebCrawler.adapter.inbound;

import de.collectioncompanion.WebCrawler.adapter.outbound.RestServerOut;
import de.collectioncompanion.WebCrawler.data_files.WebcrawlerThread;
import de.collectioncompanion.WebCrawler.ports.inbound.RestOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static de.collectioncompanion.WebCrawler.data_files.WebcrawlerThread.getCounterForActiveWebcrawlerSearches;

@RestController
@RequestMapping("/collection")
public class RestServerIn {

    /**
     * Must be public for annotation "@Autowired"
     * -> final not compatible with "@Autowired"
     * -> static not compatible with "@Autowired"
     * -> public because of using RestOut-object in other classes
     */
    @Autowired
    public RestOut restOut;

    /**
     * Must be public for annotation "@Autowired"
     * -> final not compatible with "@Autowired"
     * -> static not compatible with "@Autowired"
     * -> public because of using RestServerOut-object in other classes
     */
    @Autowired
    public RestServerOut restServerOut;

    /**
     * Search for collection and send to Composer
     *
     * @param searchTerm The term to search for in the category
     * @param id         The ID of the collection request
     * @return Return the response as string
     */
    @PostMapping
    public ResponseEntity<Integer> searchForCollection(@RequestParam String category, @RequestParam String searchTerm,
                                                       @RequestParam long id) {
        new WebcrawlerThread(category, searchTerm, id, restOut, restServerOut);
        return ResponseEntity.status(200).body(getCounterForActiveWebcrawlerSearches());
    }

}

