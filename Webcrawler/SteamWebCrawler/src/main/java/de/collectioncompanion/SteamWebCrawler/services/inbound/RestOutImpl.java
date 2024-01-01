package de.collectioncompanion.SteamWebCrawler.services.inbound;

import de.collectioncompanion.SteamWebCrawler.ports.data_files.WebCrawler;
import de.collectioncompanion.SteamWebCrawler.ports.inbound.RestOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ports.Collection;

@Service
public class RestOutImpl implements RestOut {

    @Autowired
    private Environment environment;

    /**
     * Starting of path of URI
     */
    public static final String STARTING = "/collection";

    @Autowired
    private WebCrawler webCrawler;

    @Override
    public Collection crawl(String searchTerm) {
        System.out.println("Collection request was received successfully!");
        Collection collection = webCrawler.findInformationToCollection(searchTerm);
        System.out.println("Found collection: " + collection);

        return collection;
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
