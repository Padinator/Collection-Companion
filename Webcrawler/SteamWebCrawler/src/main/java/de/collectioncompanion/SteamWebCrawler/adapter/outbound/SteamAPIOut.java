package de.collectioncompanion.SteamWebCrawler.adapter.outbound;

import de.collectioncompanion.SteamWebCrawler.ports.data_files.Collection;
import de.collectioncompanion.SteamWebCrawler.ports.data_files.WebCrawler;
import de.collectioncompanion.SteamWebCrawler.ports.outbound.SteamAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SteamAPIOut implements WebCrawler {

    @Autowired
    private SteamAPI steamAPI;

    public Collection findInformationToCollection(String searchTerm) {
        return steamAPI.findInformationToCollection(searchTerm);
    }

}
