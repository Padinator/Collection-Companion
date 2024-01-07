package de.collectioncompanion.WebCrawler.adapter.outbound;

import de.collectioncompanion.WebCrawler.ports.outbound.SteamAPI;
import de.collectioncompanion.WebCrawler.ports.outbound.WebAPICall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ports.Collection;

import java.util.List;

@Service
public class SteamAPIOut implements WebAPICall {

    @Autowired
    private SteamAPI steamAPI;

    @Override
    public List<Collection> findCollections(String searchTerm) {
        return steamAPI.findCollections(searchTerm);
    }

}
