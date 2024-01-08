package de.collectioncompanion.WebCrawler.ports.outbound;

import org.springframework.web.client.RestTemplate;
import ports.Collection;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ports.CollectionFormatter.compareGameNames;

public abstract class GameOutImpl implements GameOut {

    /**
     * Finds and returns a collection matching the search term or an empty collection, if no collection matching the
     * search term was found
     *
     * @param searchTerm The search term to look for a collection
     * @return Return the collection matching the search term or an empty collection
     */
    @Override
    public List<Collection> findCollections(String searchTerm) {
        List<Integer> appIDs = findConcernedGame(searchTerm);

        if (appIDs.size() == 0) // Cannot find a collection with passed search term
            return new LinkedList<>();


        return appIDs.stream().map(appID -> requestGameSpecificAPI(searchTerm, appID)).toList(); // Find all collections
    }

    /**
     * Creates a Map with all necessary data of a collection
     *
     * @param body The collection as json
     * @return Return a Map with all necessary key-value-pairs of a collection for games
     */
    protected abstract Map<String, String> formatCollectionData(String body);

    /**
     * Requests the API with the URL defined/used in the subclasses. Call method "requestAnAPI" with correct URL
     *
     * @param searchTerm The term to search for a collection -> it can only be correct, because of caller
     *                   "findInformationToCollection"
     * @param appID      The ID of game in the API
     * @return Return the collection matching the search term
     */
    protected abstract Collection requestGameSpecificAPI(String searchTerm, int appID);

    // Helper methods

    /**
     * Requests steam API with passed URL and returns the body of the HTTP-request
     *
     * @param url URL of HTTP-request
     * @return The body of HTTP-request as String
     */
    protected String requestAnAPI(String url) {
        return new RestTemplate().getForEntity(url, String.class).getBody();
    }

    /**
     * Searches for the appID of the requested game. Therefore, a game must be found to the passed search term
     *
     * @param searchTerm Term to search for a game
     * @return Return the appID to a game
     */
    protected List<Integer> findConcernedGame(String searchTerm) {
        List<Integer> appIDs = new LinkedList<>();

        for (Map.Entry<Integer, String> game : getAllGames().entrySet())
            if (compareGameNames(game.getValue(), searchTerm))
                appIDs.add(game.getKey());

        return appIDs;
    }

    /**
     * Removes HTML tags from passed string
     *
     * @param input Input to remove HTML tags from
     * @return Return the passed input without HTML tags
     */
    public static String removeHTMLTags(String input) {
        String regex = "<.*?>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.replaceAll("");
    }

    /**
     * Removes all HTML tags and " from each entry of passed TreeMap
     *
     * @param data with all property value pairs
     * @return the formatted collection data
     */
    public static Map<String, String> formatCollectionData(Map<String, String> data) {
        TreeMap<String, String> newData = new TreeMap<>();

        for (Map.Entry<String, String> entry : data.entrySet()) {
            String property = entry.getKey(), value = entry.getValue();

            if (value != null)
                value = removeHTMLTags(value).replaceAll("\"|'", "");

            newData.put(property, value);
        }

        return newData;
    }

}
