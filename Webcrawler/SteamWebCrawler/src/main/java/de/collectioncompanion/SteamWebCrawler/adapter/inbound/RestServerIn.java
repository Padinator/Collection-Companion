package de.collectioncompanion.SteamWebCrawler.adapter.inbound;


import de.collectioncompanion.SteamWebCrawler.ports.inbound.RestIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/collection")
public class RestServerIn {

    @Autowired
    private RestIn restIn;

    /**
     * Search for collection and send to Composer
     *
     * @param searchTerm The term to search for in the category
     * @return Return the response as string
     */
    @GetMapping
    public ResponseEntity<String> searchForCollection(@RequestParam String searchTerm) {
        return restIn.requestWebCrawler(searchTerm);
    }

}

