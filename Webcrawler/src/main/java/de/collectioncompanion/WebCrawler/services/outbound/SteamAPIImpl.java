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
        /* // Fetch data form api
		String body = requestAnAPI(BASE_URL_GET_ALL_GAMES); // Use this instead of file below
		System.out.println(body);
         */

        // Save data as object
        File allSteamGamesTxt = new File(new File("").getAbsolutePath() + "/AllSteamGames.txt");
        TreeMap<Integer, String> allGames = new TreeMap<>(); // <app-id, name>

        try {
            // Replace "reader.readLine()" with "body"
            //BufferedReader reader = new BufferedReader(new FileReader(allSteamGamesTxt));
            String apps = "{\"applist\":{\"apps\":[{\"appid\":1551830,\"name\":\"Passengers Of Execution\"}]}}";
            Map tmp = new ObjectMapper().readValue(/* reader.readLine() */ apps, Map.class); // Get list of apps

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
    protected Collection requestGameSpecificAPI(String searchTerm) {
        // Search for game data
        //String url = BASE_URL_GET_ONE_GAME + "?appids=" + appID;
        //String body = requestAnAPI(url);
        String body = "{\"1551830\":{\"success\":true,\"data\":{\"type\":\"game\",\"name\":\"Passengers Of Execution\",\"steam_appid\":1551830,\"required_age\":0,\"is_free\":false,\"detailed_description\":\"<img src=\\\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/extras\\/Artboard_1.png?t=1695025635\\\" \\/><br>Passengers of Execution is an independent game whose main theme is adventure\\/puzzle, although it contains many mechanics. It consists of maps that move along a linear line, designed in a way that you players' sense of discovery and curiosity of seeing new worlds. Sometimes you have to find the cause of death of a corpse, the shoe of a beggar, the exit of a giant maze and sometimes you have to make your hands talk.<br><br><br><ul class=\\\"bb_ul\\\"><li>Dozens of section designs with different themes and tones.<\\/li><\\/ul><ul class=\\\"bb_ul\\\"><li>Dynamic game mechanics that are constantly changing.<\\/li><\\/ul><ul class=\\\"bb_ul\\\"><li>Exciting character designs.<\\/li><\\/ul><ul class=\\\"bb_ul\\\"><li>A curious story, far from cliché and cheap tricks.<\\/li><\\/ul><ul class=\\\"bb_ul\\\"><li>Musics that changes depending on the feeling you want to be given.<\\/li><\\/ul><ul class=\\\"bb_ul\\\"><li>Level designs designed using all angles mechanically 2d \\/ 3d\\/2.5 d\\/isometric \\/ perspective.<\\/li><\\/ul><br><img src=\\\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/extras\\/Artboard_1_copy_3.png?t=1695025635\\\" \\/><br><strong>Witness the journey of an officer in the homicide department from ordinary and invariably quiet life to an unknown world. Abush's soul is trapped in the software of a computer genius, he must find an exit before his body decays.<\\/strong><br><br><img src=\\\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/extras\\/Untitled-2.png?t=1695025635\\\" \\/><br><strong>Meet artificial intelligences and characters trapped in a purgatory like themselves in this adventure.<\\/strong><br><br><img src=\\\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/extras\\/Artboard_1_copy.png?t=1695025635\\\" \\/><br><strong>Pass through the strange and exceptional level designs successfully<\\/strong><br><br>Sometimes things go wrong! Who knows, maybe one day you'll be stuck between life and death because of the stupidity of a cyber hacker. <strong>Do you think I'm kidding?<\\/strong>\",\"about_the_game\":\"<img src=\\\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/extras\\/Artboard_1.png?t=1695025635\\\" \\/><br>Passengers of Execution is an independent game whose main theme is adventure\\/puzzle, although it contains many mechanics. It consists of maps that move along a linear line, designed in a way that you players' sense of discovery and curiosity of seeing new worlds. Sometimes you have to find the cause of death of a corpse, the shoe of a beggar, the exit of a giant maze and sometimes you have to make your hands talk.<br><br><br><ul class=\\\"bb_ul\\\"><li>Dozens of section designs with different themes and tones.<\\/li><\\/ul><ul class=\\\"bb_ul\\\"><li>Dynamic game mechanics that are constantly changing.<\\/li><\\/ul><ul class=\\\"bb_ul\\\"><li>Exciting character designs.<\\/li><\\/ul><ul class=\\\"bb_ul\\\"><li>A curious story, far from cliché and cheap tricks.<\\/li><\\/ul><ul class=\\\"bb_ul\\\"><li>Musics that changes depending on the feeling you want to be given.<\\/li><\\/ul><ul class=\\\"bb_ul\\\"><li>Level designs designed using all angles mechanically 2d \\/ 3d\\/2.5 d\\/isometric \\/ perspective.<\\/li><\\/ul><br><img src=\\\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/extras\\/Artboard_1_copy_3.png?t=1695025635\\\" \\/><br><strong>Witness the journey of an officer in the homicide department from ordinary and invariably quiet life to an unknown world. Abush's soul is trapped in the software of a computer genius, he must find an exit before his body decays.<\\/strong><br><br><img src=\\\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/extras\\/Untitled-2.png?t=1695025635\\\" \\/><br><strong>Meet artificial intelligences and characters trapped in a purgatory like themselves in this adventure.<\\/strong><br><br><img src=\\\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/extras\\/Artboard_1_copy.png?t=1695025635\\\" \\/><br><strong>Pass through the strange and exceptional level designs successfully<\\/strong><br><br>Sometimes things go wrong! Who knows, maybe one day you'll be stuck between life and death because of the stupidity of a cyber hacker. <strong>Do you think I'm kidding?<\\/strong>\",\"short_description\":\"Witness the journey of an officer in the homicide department from ordinary and invariably quiet life to an unknown world. Passengers of Execution is an independent game whose main theme is adventure\\/puzzle, although it contains many mechanics.\",\"supported_languages\":\"Englisch, Türkisch\",\"header_image\":\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/header.jpg?t=1695025635\",\"capsule_image\":\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/capsule_231x87.jpg?t=1695025635\",\"capsule_imagev5\":\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/capsule_184x69.jpg?t=1695025635\",\"website\":\"https:\\/\\/www.parterdizayn.com\",\"pc_requirements\":{\"minimum\":\"<strong>Mindestanforderungen:<\\/strong><br><ul class=\\\"bb_ul\\\"><li><strong>Betriebssystem *:<\\/strong> Microsoft® Windows® 7 \\/ 8 \\/ 10 (32-bit\\/64-bit)<br><\\/li><li><strong>Prozessor:<\\/strong> 2.0 GHz or faster processor<br><\\/li><li><strong>Arbeitsspeicher:<\\/strong> 4 GB RAM<br><\\/li><li><strong>Grafik:<\\/strong> 1366 x 768 pixels or higher desktop resolution<br><\\/li><li><strong>DirectX:<\\/strong> Version 9.0<br><\\/li><li><strong>Speicherplatz:<\\/strong> 400 MB verfügbarer Speicherplatz<\\/li><\\/ul>\",\"recommended\":\"<strong>Empfohlen:<\\/strong><br><ul class=\\\"bb_ul\\\"><li><strong>Betriebssystem *:<\\/strong> Microsoft® Windows® 7 \\/ 8 \\/ 10 (32-bit\\/64-bit)<br><\\/li><li><strong>Prozessor:<\\/strong> 2.0 GHz or faster processor<br><\\/li><li><strong>Arbeitsspeicher:<\\/strong> 8 GB RAM<br><\\/li><li><strong>Grafik:<\\/strong> 1920 x 1080 pixels or higher desktop resolution<br><\\/li><li><strong>DirectX:<\\/strong> Version 11<br><\\/li><li><strong>Speicherplatz:<\\/strong> 2000 MB verfügbarer Speicherplatz<\\/li><\\/ul>\"},\"mac_requirements\":[],\"linux_requirements\":[],\"developers\":[\"Mustafa CELIK\"],\"publishers\":[\"Parter Dizayn\"],\"price_overview\":{\"currency\":\"EUR\",\"initial\":399,\"final\":67,\"discount_percent\":83,\"initial_formatted\":\"3,99€\",\"final_formatted\":\"0,67€\"},\"packages\":[547986],\"package_groups\":[{\"name\":\"default\",\"title\":\"Passengers Of Execution kaufen\",\"description\":\"\",\"selection_text\":\"Kaufoption auswählen\",\"save_text\":\"\",\"display_type\":0,\"is_recurring_subscription\":\"false\",\"subs\":[{\"packageid\":547986,\"percent_savings_text\":\"-83% \",\"percent_savings\":0,\"option_text\":\"Passengers Of Execution - <span class=\\\"discount_original_price\\\">3,99€<\\/span> 0,67€\",\"option_description\":\"\",\"can_get_free_license\":\"0\",\"is_free_license\":false,\"price_in_cents_with_discount\":67}]}],\"platforms\":{\"windows\":true,\"mac\":false,\"linux\":false},\"categories\":[{\"id\":2,\"description\":\"Einzelspieler\"},{\"id\":22,\"description\":\"Steam-Errungenschaften\"},{\"id\":23,\"description\":\"Steam Cloud\"}],\"genres\":[{\"id\":\"25\",\"description\":\"Abenteuer\"},{\"id\":\"4\",\"description\":\"Gelegenheitsspiele\"}],\"screenshots\":[{\"id\":0,\"path_thumbnail\":\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/ss_180e0bc8323a5d471b3958834cb6257bf3b1e379.600x338.jpg?t=1695025635\",\"path_full\":\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/ss_180e0bc8323a5d471b3958834cb6257bf3b1e379.1920x1080.jpg?t=1695025635\"},{\"id\":1,\"path_thumbnail\":\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/ss_a4aabd0a67f376260028e29a9e1677e15f75d72b.600x338.jpg?t=1695025635\",\"path_full\":\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/ss_a4aabd0a67f376260028e29a9e1677e15f75d72b.1920x1080.jpg?t=1695025635\"},{\"id\":2,\"path_thumbnail\":\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/ss_e2555876cdb6a3f27e29f9ba0d1703f4b992ac37.600x338.jpg?t=1695025635\",\"path_full\":\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/ss_e2555876cdb6a3f27e29f9ba0d1703f4b992ac37.1920x1080.jpg?t=1695025635\"},{\"id\":3,\"path_thumbnail\":\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/ss_f146c887316418bb93444abc73051351364fe1a4.600x338.jpg?t=1695025635\",\"path_full\":\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/ss_f146c887316418bb93444abc73051351364fe1a4.1920x1080.jpg?t=1695025635\"},{\"id\":4,\"path_thumbnail\":\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/ss_e701827648884b357431346aef0c51aed32699d2.600x338.jpg?t=1695025635\",\"path_full\":\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/ss_e701827648884b357431346aef0c51aed32699d2.1920x1080.jpg?t=1695025635\"}],\"movies\":[{\"id\":256865126,\"name\":\"Trailer\",\"thumbnail\":\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/256865126\\/movie.293x165.jpg?t=1639504227\",\"webm\":{\"480\":\"http:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/256865126\\/movie480_vp9.webm?t=1639504227\",\"max\":\"http:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/256865126\\/movie_max_vp9.webm?t=1639504227\"},\"mp4\":{\"480\":\"http:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/256865126\\/movie480.mp4?t=1639504227\",\"max\":\"http:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/256865126\\/movie_max.mp4?t=1639504227\"},\"highlight\":true}],\"achievements\":{\"total\":3,\"highlighted\":[{\"name\":\"We're getting started\",\"path\":\"https:\\/\\/cdn.akamai.steamstatic.com\\/steamcommunity\\/public\\/images\\/apps\\/1551830\\/c5653ec1b3a4701e89c9e85eac96f2eba0df90c9.jpg\"},{\"name\":\"Another world\",\"path\":\"https:\\/\\/cdn.akamai.steamstatic.com\\/steamcommunity\\/public\\/images\\/apps\\/1551830\\/5904ba5cc7828d2d5630a3c6212ff383982018a1.jpg\"},{\"name\":\"It's up to me\",\"path\":\"https:\\/\\/cdn.akamai.steamstatic.com\\/steamcommunity\\/public\\/images\\/apps\\/1551830\\/9ccb1ad16deec735cf3f08fa9c82140be5fea37e.jpg\"}]},\"release_date\":{\"coming_soon\":false,\"date\":\"22. Dez. 2021\"},\"support_info\":{\"url\":\"https:\\/\\/www.parterdizayn.com\\/iletisim\",\"email\":\"celik12256@gmail.com\"},\"background\":\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/page_bg_generated_v6b.jpg?t=1695025635\",\"background_raw\":\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/page.bg.jpg?t=1695025635\",\"content_descriptors\":{\"ids\":[],\"notes\":null}}}}";

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
    protected Map<String, String> formatCollectionData(String body) {
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
