package de.collectioncompanion.WebCrawler.adapter.outbound;

import de.collectioncompanion.WebCrawler.ports.outbound.RAWGioOut;
import de.collectioncompanion.WebCrawler.ports.outbound.WebAPICall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ports.Collection;

import java.util.List;

@Service
public class RAWGioAPIOut implements WebAPICall {

    @Autowired
    private RAWGioOut rawGioOut;

    @Override
    public List<Collection> findCollections(String searchTerm) {
        return rawGioOut.findCollections(searchTerm);
    }

}
