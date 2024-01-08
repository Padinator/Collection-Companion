package de.collectioncompanion.WebCrawler.services.outbound;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import data_files.CollectionImpl;
import data_files.GameCollectionFormatter;
import de.collectioncompanion.WebCrawler.ports.outbound.GameOutImpl;
import de.collectioncompanion.WebCrawler.ports.outbound.RAWGioOut;
import org.springframework.stereotype.Service;
import ports.Collection;

import java.util.*;

import static ports.CollectionFormatter.compareGameNames;

@Service
public class RAWGioOutImpl extends GameOutImpl implements RAWGioOut {

    private final static String BASE_URL_GET_ALL_GAMES = "https://api.rawg.io/api/games";
    private final static String BASE_URL_GET_ONE_GAME = "https://api.rawg.io/api/games/"; // {id}
    private final static String API_KEY = "4124a8f6f9be4b5a8f33ac3e965d227c"; // ?key=

    /**
     * Maximum count of returned/used games in a response to rawg.io
     */
    private final static int MAX_RESULTING_ENTRIES = 5;

    /**
     * Finds and returns a collection matching the search term or an empty collection, if no collection matching the
     * search term was found. The search term is assumed to be correct -> rawg.io has a "fuzziness search"
     *
     * @param searchTerm The search term to look for a collection, must be correct
     * @return Return the collection matching the search term or an empty collection
     */
    @Override
    public List<Collection> findCollections(String searchTerm) {
        String urlWithSearchTerm = BASE_URL_GET_ALL_GAMES + "?search=" + searchTerm + "&key=" + API_KEY;
        String foundGames = requestAnAPI(urlWithSearchTerm);
        String urlToGame, body = "";
        List<Integer> appIDs = new LinkedList<>();

        // Filter returned games for first matching game
        try {
            ArrayList results = (ArrayList) new ObjectMapper().readValue(foundGames, LinkedHashMap.class).get("results");

            for (int i = 0; i < MAX_RESULTING_ENTRIES; ++i)
                if (compareGameNames(String.valueOf(((LinkedHashMap) results.get(i)).get("name")), searchTerm))
                    appIDs.add(Integer.parseInt(String.valueOf(((LinkedHashMap) results.get(i)).get("id")))); // Find ID of the game
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return appIDs.stream().map(appID -> requestGameSpecificAPI(searchTerm, appID)).toList(); // Find all collections
    }

    @Override
    public TreeMap<Integer, String> getAllGames() {
        throw new UnsupportedOperationException("Rawg.io does not support getting all games -> requires requesting "
                + "all 800000 games with ~1000 requests per page so 800 pages");
    }

    @Override
    public Collection requestGameSpecificAPI(String searchTerm, int appID) {
        // Request game with its ID
        String urlToGame = BASE_URL_GET_ONE_GAME + appID + "?key=" + API_KEY;
        System.out.println(urlToGame);
        String body = requestAnAPI(urlToGame);

        /*
        File game = new File(new File("").getAbsolutePath() + "/rawgio_game1_details.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(game));
            body = reader.lines().collect(Collectors.joining());
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
         */

        return new CollectionImpl(formatCollectionData(body)); // Return the collection with correct format
    }

    @Override
    public Map<String, String> formatCollectionData(String body) {
        GameCollectionFormatter formatter = new GameCollectionFormatter();
        Map<String, String> collectionData = new TreeMap<>();

        try {
            LinkedHashMap gameData = new ObjectMapper().readValue(body, LinkedHashMap.class);

            // Title
            collectionData.put(formatter.getPropertyName("title"), (String) gameData.get("name"));

            // Main image
            collectionData.put(formatter.getPropertyName("main_img"), (String) gameData
                    .get("background_image"));

            // Short description
            collectionData.put(formatter.getPropertyName("short_description"), null);

            // Detailed description
            collectionData.put(formatter.getPropertyName("detailed_description"),
                    (String) gameData.get("description_raw"));

            // Required age
            collectionData.put(formatter.getPropertyName("required_age"), null);

            // Supported languages
            collectionData.put(formatter.getPropertyName("languages"), null);

            // Genres
            String genres = ((ArrayList) gameData.get("genres")).stream()
                    .map(entry -> (String) ((LinkedHashMap) entry).get("name")).toList().toString();
            collectionData.put(formatter.getPropertyName("genres"), genres);

            // Platforms
            String availablePlatforms = ((ArrayList) gameData.get("platforms")).stream()
                    .map(entry -> (String) ((LinkedHashMap) entry).get("name")).toList().toString();
            collectionData.put(formatter.getPropertyName("available_platforms"), availablePlatforms);

            // Processor
            collectionData.put(formatter.getPropertyName("necessary_processor"), null);

            // RAM
            collectionData.put(formatter.getPropertyName("necessary_ram"), null);

            // Memory
            collectionData.put(formatter.getPropertyName("necessary_memory"), null);

            // Desktop resolution
            collectionData.put(formatter.getPropertyName("necessary_desktop_resolution"), null);

            /*
             * Additional information
             */
            // Developers
            String developers = ((ArrayList) gameData.get("developers")).stream()
                    .map(entry -> (String) ((LinkedHashMap) entry).get("name")).toList().toString();
            collectionData.put(formatter.getOptionalPropertyName("developers"), developers);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return collectionData;
    }

}
