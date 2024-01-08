package de.collectioncompanion.WebCrawler.services.outbound;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import data_files.CollectionImpl;
import data_files.GameCollectionFormatter;
import de.collectioncompanion.WebCrawler.ports.outbound.GameOutImpl;
import de.collectioncompanion.WebCrawler.ports.outbound.SteamAPI;
import org.springframework.stereotype.Service;
import ports.Collection;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SteamAPIImpl extends GameOutImpl implements SteamAPI {

    private final static String BASE_URL_GET_ALL_GAMES = "https://api.steampowered.com/ISteamApps/GetAppList/v2/";
    private final static String BASE_URL_GET_ONE_GAME = "https://store.steampowered.com/api/appdetails";

    @Override
    public TreeMap<Integer, String> getAllGames() {
        // Fetch data form api
		String body = requestAnAPI(BASE_URL_GET_ALL_GAMES); // Use this instead of file below
		System.out.println(body);

        // Save data as object
        File allSteamGamesTxt = new File(new File("").getAbsolutePath() + "/AllSteamGames.txt");
        TreeMap<Integer, String> allGames = new TreeMap<>(); // <app-id, name>

        try {
            // Replace "reader.readLine()" with "body"
            //BufferedReader reader = new BufferedReader(new FileReader(allSteamGamesTxt));
            // String apps = "{\"applist\":{\"apps\":[{\"appid\":1551830,\"name\":\"Passengers Of Execution\"}]}}";
            Map tmp = new ObjectMapper().readValue(/* reader.readLine() */ /* apps */ body, Map.class); // Get list of apps

            ((List) ((LinkedHashMap) tmp.get("applist")).get("apps")).forEach(game -> {
                if (game instanceof LinkedHashMap gameEntry) {
                    int appID = (Integer) gameEntry.get("appid");
                    String gameName = (String) gameEntry.get("name");
                    allGames.put(appID, gameName);
                }
            });

            //reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return allGames;
    }

    @Override
    public Collection requestGameSpecificAPI(String searchTerm, int appID) {
        // Search for game data
        String url = BASE_URL_GET_ONE_GAME + "?appids=" + appID;
        String body = requestAnAPI(url);

        // Must be removed later
        /*
        try {
            File aGameTxt = new File(new File("").getAbsolutePath() + "/game1.txt");
            BufferedReader reader = new BufferedReader(new FileReader(aGameTxt));

            body = reader.readLine();
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
         */

        return new CollectionImpl(formatCollectionData(body));
    }

    /**
     * Formats the attributes of the HTTP-response
     *
     * @param body Body of HTTP-response = data of a game
     * @return Return the formatted data as Map
     */
    @Override
    public Map<String, String> formatCollectionData(String body) {
        GameCollectionFormatter formatter = new GameCollectionFormatter();
        Map<String, String> collectionData = new TreeMap<>();

        try {
            TreeMap rawBody = new ObjectMapper().readValue(body, TreeMap.class);
            LinkedHashMap rawData = (LinkedHashMap) ((Map.Entry) rawBody.entrySet().iterator().next()).getValue();
            LinkedHashMap gameData = (LinkedHashMap) rawData.get("data");

            // Title
            collectionData.put(formatter.getPropertyName("title"), (String) gameData.get("name"));

            // Main image
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
            String languages = Arrays.toString(((String) gameData.get("supported_languages")).split(", "));
            collectionData.put(formatter.getPropertyName("languages"), languages);

            // Genres
            String genres = ((ArrayList) gameData.get("genres")).stream()
                    .map(map -> ((LinkedHashMap) map).get("description")).collect(Collectors.toList()).toString();
            collectionData.put(formatter.getPropertyName("genres"), genres);

            // Get all PC requirements
            String[] pcRequirements = ((String) ((LinkedHashMap) gameData.get("pc_requirements")).get("minimum"))
                    .split("<br>");

            // Platforms
            String availablePlatforms = removeHTMLTags(pcRequirements[1].replaceAll(".*: ", ""));
            collectionData.put(formatter.getPropertyName("available_platforms"), availablePlatforms);

            // Processor
            String necessaryProcessor = removeHTMLTags(pcRequirements[2].replaceAll(".*: ", ""));
            collectionData.put(formatter.getPropertyName("necessary_processor"), necessaryProcessor);

            // RAM
            String necessaryRam = removeHTMLTags(pcRequirements[3].replaceAll(".*: ", ""));
            collectionData.put(formatter.getPropertyName("necessary_ram"), necessaryRam);

            // Memory
            String necessaryMemory = removeHTMLTags(pcRequirements[4].replaceAll(".*: ", ""));
            collectionData.put(formatter.getPropertyName("necessary_memory"), necessaryMemory);

            // Desktop resolution
            String necessaryDesktop_resolution = removeHTMLTags(pcRequirements[5].replaceAll(".*: ", ""));
            collectionData.put(formatter.getPropertyName("necessary_desktop_resolution"), necessaryDesktop_resolution);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return collectionData;
    }

}
