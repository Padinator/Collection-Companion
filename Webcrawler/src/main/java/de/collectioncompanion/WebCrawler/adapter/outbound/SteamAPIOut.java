package de.collectioncompanion.WebCrawler.adapter.outbound;

import de.collectioncompanion.WebCrawler.ports.data_files.WebCrawler;
import de.collectioncompanion.WebCrawler.ports.outbound.SteamAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ports.Collection;

@Service
public class SteamAPIOut implements WebCrawler {

    @Autowired
    private SteamAPI steamAPI;

    public Collection findInformationToCollection(String searchTerm) {
        return steamAPI.findInformationToCollection(searchTerm);
    }

}
