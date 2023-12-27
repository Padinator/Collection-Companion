package de.collectioncompanion.SteamWebCrawler.adapter.outbound;

import de.collectioncompanion.SteamWebCrawler.ports.data_files.Collection;
import de.collectioncompanion.SteamWebCrawler.ports.outbound.SteamAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.TreeMap;


@Service
public class SteamAPIOut {

    @Autowired
    private SteamAPI steamAPI;

    public Collection findInformationToCollection(String searchTerm) {
        return steamAPI.findInformationToCollection(searchTerm);
    }
}
