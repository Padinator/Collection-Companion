package de.collectioncompanion.WebCrawler.adapter.outbound;

import de.collectioncompanion.WebCrawler.ports.outbound.WebAPICall;
import de.collectioncompanion.WebCrawler.ports.outbound.RAWGioOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ports.Collection;

@Service
public class RAWGioAPIOut implements WebAPICall {

    @Autowired
    private RAWGioOut rawGioOut;

    public Collection findInformationToCollection(String searchTerm) {
        return rawGioOut.findInformationToCollection(searchTerm);
    }

}
