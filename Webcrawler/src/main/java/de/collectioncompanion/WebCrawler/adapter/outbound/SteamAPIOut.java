package de.collectioncompanion.WebCrawler.adapter.outbound;

import de.collectioncompanion.WebCrawler.ports.outbound.WebAPICall;
import de.collectioncompanion.WebCrawler.ports.outbound.SteamAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ports.Collection;

@Service
public class SteamAPIOut implements WebAPICall {

    @Autowired
    private SteamAPI steamAPI;

    public Collection findInformationToCollection(String searchTerm) {
        return steamAPI.findInformationToCollection(searchTerm);
    }

}
