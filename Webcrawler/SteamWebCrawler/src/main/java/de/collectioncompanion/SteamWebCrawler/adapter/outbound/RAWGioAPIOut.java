package de.collectioncompanion.SteamWebCrawler.adapter.outbound;

import de.collectioncompanion.SteamWebCrawler.ports.data_files.WebCrawler;
import de.collectioncompanion.SteamWebCrawler.ports.outbound.RAWGioOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ports.Collection;

@Service
public class RAWGioAPIOut implements WebCrawler {

    @Autowired
    private RAWGioOut rawGioOut;

    public Collection findInformationToCollection(String searchTerm) {
        return rawGioOut.findInformationToCollection(searchTerm);
    }

}
