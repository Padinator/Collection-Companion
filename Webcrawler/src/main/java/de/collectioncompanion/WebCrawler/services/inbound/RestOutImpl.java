package de.collectioncompanion.WebCrawler.services.inbound;

import data_files.Levenshtein;
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
import ports.CollectionFormatter;

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
    public List<Collection> crawlGame(String searchTerm) {
        List<Collection> collections = new LinkedList<>();
        collections.addAll(steamAPIOut.findCollections(searchTerm));
        collections.addAll(rawGioOut.findCollections(searchTerm));
        System.out.println("\n\nBefore collections " + collections);
        System.out.println("\n\nMerged collections: " + merge(collections));
        return merge(collections);
    }

    @Override
    public List<Collection> crawlMovie(String searchTerm) {
        List<Collection> collections = new LinkedList<>();
        return merge(collections);
    }

    @Override
    public List<Collection> crawlSeries(String searchTerm) {
        List<Collection> collections = new LinkedList<>();
        return merge(collections);
    }

    @Override
    public List<Collection> crawlComic(String searchTerm) {
        List<Collection> collections = new LinkedList<>();
        return merge(collections);
    }

    /**
     * Merge resulting collections based on "Levenshtein Distance"
     *
     * @param collections All searched collections from each web page
     * @return Return a list containing merged collections
     */
    public static List<Collection> merge(List<Collection> collections) {
        List<Collection> mergedCollections = new LinkedList<>();

        // Insert all key value pairs, if the key does not exist in merged collection yet
        for (Collection collection : collections) {
            int leastDistance = -1; // Least distance of tow collection titles -> merge these
            Collection collectionToMerge = null;

            // Find, if another collection is mostly equal to "collection" or collection must be inserted/is new
            for (Collection mergedCollection : mergedCollections) {
                String title1 = mergedCollection.getValue("title");
                String title2 = collection.getValue("title");
                int distance = Levenshtein.calculateDistance(title1, title2);

                if (CollectionFormatter.compareGameNames(title1, title2) // Collection titles are mostly equal
                        && (collectionToMerge == null || distance < leastDistance)) { // Collections must be more equal than the last "collectionToMerge"
                    leastDistance = distance;
                    collectionToMerge = mergedCollection;
                }
            }

            // Insert collection raw or merge it into another one
            if (collectionToMerge == null) // If "collection must be inserted, because it is new/not yet inserted
                mergedCollections.add(collection);
            else // Merge "collection" to "mergedCollection"
                for (Map.Entry<String, String> pair : collection.getData().entrySet())
                    if (!collectionToMerge.containsKey(pair.getKey()))
                        collectionToMerge.putEntry(pair.getKey(), pair.getValue());
        }

        return mergedCollections;
    }

    @Override
    public void response(long id, List<Collection> collections) {
        final String COMPOSER_MS = "http://" + environment.getProperty("COMPOSER_MS") + STARTING + "?id=" + id;
        //final String COMPOSER_MS = "http://localhost:8084/collection?id=" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<Collection>> request = new HttpEntity<>(collections, headers);

        System.out.println("URI to call post request: " + COMPOSER_MS);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(COMPOSER_MS, request, String.class);
    }

}
