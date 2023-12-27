package de.collectioncompanion.SteamWebCrawler.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.collectioncompanion.SteamWebCrawler.data_files.CollectionImpl;
import de.collectioncompanion.SteamWebCrawler.ports.data_files.Collection;
import de.collectioncompanion.SteamWebCrawler.ports.outbound.SteamAPI;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class SteamAPIImpl implements SteamAPI {

    private final static String BASE_URL_GET_ALL_GAMES = "https://api.steampowered.com/ISteamApps/GetAppList/v2/";
    private final static String BASE_URL_GET_ONE_GAME = "https://store.steampowered.com/api/appdetails";

    @Override
    public TreeMap<Integer, String> getAllGames() {
        /* // Fetch data form api
		String body = requestAPI(BASE_URL_GET_ALL_GAMES); // Use this instead of file below
		System.out.println(body);
         */

        // Save data as object
        File allSteamGamesTxt = new File(new File("").getAbsolutePath() + "\\AllSteamGames.txt");
        TreeMap<Integer, String> allGames = new TreeMap<>(); // <app-id, name>

        try {
            // Replace "reader.readLine()" with "body"
            BufferedReader reader = new BufferedReader(new FileReader(allSteamGamesTxt));
            Map tmp = new ObjectMapper().readValue(reader.readLine(), Map.class); // Get list of apps

            ((List) ((LinkedHashMap) tmp.get("applist")).get("apps")).forEach(game -> {
                if (game instanceof LinkedHashMap gameEntry) {
                    int appID = (Integer) gameEntry.get("appid");
                    String gameName = (String) gameEntry.get("name");
                    allGames.put(appID, gameName);
                }
            });

            System.out.println(allGames.size());
            System.out.println(allGames.get(1551830));

            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return allGames;
    }

    @Override
    public Collection findInformationToCollection(String searchTerm) {
        int appID = findConcernedGame(searchTerm);

        if (appID == -1) // No collection was found
            return new CollectionImpl(new TreeMap<>());

        // Search for game data
        //String url = BASE_URL_GET_ONE_GAME + "?appids=" + appID;
        //String body = requestAPI(url);
        String body = "";

        // Must be removed later
        try {
            File allSteamGamesTxt = new File(new File("").getAbsolutePath() + "\\game1.txt");
            BufferedReader reader = new BufferedReader(new FileReader(allSteamGamesTxt));

            body = reader.readLine();
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new CollectionImpl(formatCollectionData(body));
    }

    /**
     * Requests steam API with passed URL and returns the body of the HTTP-request
     *
     * @param url URL of HTTP-request
     * @return The body of HTTP-request as String
     */
    private String requestAPI(String url) {
        return new RestTemplate().getForEntity(url, String.class).getBody();
    }

    /**
     * Searches for the appID of the requested game. Therefore, a game must be found to the passed seach term
     *
     * @param searchTerm Term to search for a game
     * @return Return the appID to a game
     */
    private int findConcernedGame(String searchTerm) {
        for (Map.Entry<Integer, String> game : getAllGames().entrySet())
            if (game.getValue().equals(searchTerm))
                return game.getKey();

        return -1;
    }

    /**
     * Formats the attributes of the HTTP-response
     *
     * @param body Body of HTTP-response = data of a game
     * @return Return the formatted data as Map
     */
    private Map<String, String> formatCollectionData(String body) {
        Map<String, String> collectionData = new TreeMap<>();

        collectionData.put("time_stamp", String.valueOf(System.currentTimeMillis()));
        collectionData.put("data", body); // For starting only two entries

        return collectionData;
    }
}
