package de.collectioncompanion.WebCrawler.services.inbound;

import data_files.CollectionImpl;
import de.collectioncompanion.WebCrawler.adapter.outbound.SteamAPIOut;
import de.collectioncompanion.WebCrawler.ports.inbound.RestOut;
import de.collectioncompanion.WebCrawler.ports.outbound.RAWGioOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ports.Collection;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class RestOutImpl implements RestOut {

    @Autowired
    private Environment environment;

    /**
     * Starting of path of URI
     */
    public static final String STARTING = "/collection";

    @Autowired
    private SteamAPIOut steamAPIOut;

    @Autowired
    private RAWGioOut rawGioOut;

    @Override
    public Collection crawlGame(String searchTerm) {
        List<Collection> collections = new LinkedList<>();
        collections.add(steamAPIOut.findInformationToCollection(searchTerm));
        collections.add(rawGioOut.findInformationToCollection(searchTerm));
        System.out.println(merge(collections));
        return merge(collections);
    }

    @Override
    public Collection crawlMovie(String searchTerm) {
        List<Collection> collections = new LinkedList<>();
        return merge(collections);
    }

    @Override
    public Collection crawlSeries(String searchTerm) {
        List<Collection> collections = new LinkedList<>();
        return merge(collections);
    }

    @Override
    public Collection crawlComic(String searchTerm) {
        List<Collection> collections = new LinkedList<>();
        return merge(collections);
    }

    /**
     * Merge resulting collections in order of the List -> attributes of first collection will be used completely
     *
     * @param collections All searched collections from each page
     * @return Return one collection containing information of all collections
     */
    private Collection merge(List<Collection> collections) {
        Collection mergedCollection = new CollectionImpl();

        // Insert all key value pairs, if the key does not exist in merged collection yet
        for (Collection collection : collections)
            for (Map.Entry<String, String> pair : collection.getData().entrySet())
                if (!mergedCollection.containsKey(pair.getKey()))
                    mergedCollection.putEntry(pair.getKey(), pair.getValue());

        return mergedCollection;
    }

    @Override
    public void response(long id, Collection collection) {
        final String COMPOSER_MS = "http://" + environment.getProperty("COMPOSER_MS") + STARTING + "?id=" + id;
        //final String COMPOSER_MS = "http://localhost:8084/collection?id=" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Collection> request = new HttpEntity<>(collection, headers);

        System.out.println("URI to call post request: " + COMPOSER_MS);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(COMPOSER_MS, request, String.class);
    }

}
