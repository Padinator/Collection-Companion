package de.collectioncompanion.SteamWebCrawler.services.inbound;

import de.collectioncompanion.SteamWebCrawler.ports.data_files.Collection;
import de.collectioncompanion.SteamWebCrawler.ports.data_files.WebCrawler;
import de.collectioncompanion.SteamWebCrawler.ports.inbound.RestIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RestInImpl implements RestIn {

    @Autowired
    private WebCrawler webCrawler;

    @Override
    public ResponseEntity<String> requestWebCrawler(String searchTerm) {
        System.out.println("Collection request was received successfully!");
        Collection collection = webCrawler.findInformationToCollection(searchTerm);
        System.out.println("Found collection: " + collection);

        // return ResponseEntity.status(200).body(collection.toString());
        return ResponseEntity.status(200).body("Collection was created!");
    }
}
