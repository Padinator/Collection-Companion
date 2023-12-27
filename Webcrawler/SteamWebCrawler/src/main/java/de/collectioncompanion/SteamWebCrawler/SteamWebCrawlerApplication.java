package de.collectioncompanion.SteamWebCrawler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.json.GsonJsonParser;

import java.io.*;
import java.util.*;

@SpringBootApplication
public class SteamWebCrawlerApplication {

	public static void main(String[] args) {
		// SpringApplication.run(SteamWebCrawlerApplication.class, args);

		/* Fetch data form api
		String url = "https://api.steampowered.com/ISteamApps/GetAppList/v2/";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		String body = response.getBody(); // Use this instead of file below
		System.out.println(body);
		*/

		// Save data as object
		File allSteamGamesTxt = new File(new File("").getAbsolutePath() + "\\AllSteamGames.txt");
		Map<Integer, String> allSteamGames = new TreeMap<>(); // <app-id, name>

		try {
			// Replace "reader.readLine()" with "body"
			BufferedReader reader = new BufferedReader(new FileReader(allSteamGamesTxt));
			Map tmp = new ObjectMapper().readValue(reader.readLine(), Map.class); // Get list of apps

			((List) ((LinkedHashMap) tmp.get("applist")).get("apps")).forEach(game -> {
				if (game instanceof LinkedHashMap gameEntry) {
					int appID = (Integer) gameEntry.get("appid");
					String gameName = (String) gameEntry.get("name");
					allSteamGames.put(appID, gameName);
				}
			});

			System.out.println(allSteamGames.size());
			System.out.println(allSteamGames.get(1551830));

			reader.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
