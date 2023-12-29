package de.collectioncompanion.SteamWebCrawler.services.outbound;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.collectioncompanion.SteamWebCrawler.data_files.CollectionImpl;
import de.collectioncompanion.SteamWebCrawler.data_files.GameCollectionFormatter;
import de.collectioncompanion.SteamWebCrawler.ports.data_files.Collection;
import de.collectioncompanion.SteamWebCrawler.ports.outbound.SteamAPI;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
        GameCollectionFormatter formatter = new GameCollectionFormatter();
        Map<String, String> collectionData = new TreeMap<>();

        try {
            TreeMap rawBody = new ObjectMapper().readValue(body, TreeMap.class);
            LinkedHashMap rawData = (LinkedHashMap) ((Map.Entry) rawBody.entrySet().iterator().next()).getValue();
            LinkedHashMap gameData = (LinkedHashMap) rawData.get("data");

            // Name
            collectionData.put(formatter.getPropertyName("title"), (String) gameData.get("name"));

            // Title
            collectionData.put(formatter.getPropertyName("main_img"), (String) gameData.get("header_image"));

            // Short description
            collectionData.put(formatter.getPropertyName("short_description"), (String) gameData.get("short_description"));

            // Detailed description
            String detailedDescription = removeHTMLTags((String) gameData.get("detailed_description"));
            collectionData.put(formatter.getPropertyName("detailed_description"), detailedDescription);

            // Required age
            collectionData.put(formatter.getPropertyName("required_age"), String.valueOf(gameData.get("required_age")));

            // Price
            collectionData.put(formatter.getPropertyName("price"),
                    (String) ((LinkedHashMap) gameData.get("price_overview")).get("final_formatted"));

            // Supported languages
            collectionData.put(formatter.getPropertyName("languages"), (String) gameData.get("supported_languages"));

            // Genres
            String genres = ((ArrayList) gameData.get("genres")).stream().map(map -> ((LinkedHashMap) map).get("description")).collect(Collectors.toList()).toString();
            collectionData.put(formatter.getPropertyName("genres"), genres);

            ((LinkedHashMap) gameData.get("pc_requirements")).get("minimum");

            /*
            collectionData.put(formatter.getPropertyName("available_platforms"), body);
            collectionData.put(formatter.getPropertyName("necessary_processor"), body);
            collectionData.put(formatter.getPropertyName("necessary_ram"), body);
            collectionData.put(formatter.getPropertyName("necessary_memory"), body);
            collectionData.put(formatter.getPropertyName("necessary_desktop_resolution"), body);
             */
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return collectionData;
    }

    private static String removeHTMLTags(String input) {
        String regex = "<.*?>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.replaceAll("");
    }
}

/*
            Pattern pattern = Pattern.compile( "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
                            + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
                            + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            Matcher matcher = pattern.matcher((String) gameData.get("header_image"));
            while (matcher.find())
                System.out.println(matcher.group());
 */