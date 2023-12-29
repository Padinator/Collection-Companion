package de.collectioncompanion.SteamWebCrawler.ports.inbound;

import org.springframework.http.ResponseEntity;

public interface RestIn {

    ResponseEntity<String> requestWebCrawler(String searchTerm);

}
