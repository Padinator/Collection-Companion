package de.collectioncompanion.WebCrawler.ports.outbound;

import data_files.CollectionImpl;
import org.springframework.web.client.RestTemplate;
import ports.Collection;

import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class GameOutImpl implements GameOut {

    /**
     * Finds and returns a collection matching the search term or an empty collection, if no collection matching the
     * search term was found
     *
     * @param searchTerm The search term to look for a collection
     * @return Return the collection matching the search term or an empty collection
     */
    @Override
    public Collection findInformationToCollection(String searchTerm) {
        int appID = findConcernedGame(searchTerm);

        if (appID == -1) // Cannot find a collection with passed search term
            return new CollectionImpl(new TreeMap<>());

        return requestGameSpecificAPI(searchTerm); // The collection exists
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
     * @return Return the collection matching the search term
     */
    protected abstract Collection requestGameSpecificAPI(String searchTerm);

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
    protected int findConcernedGame(String searchTerm) {
        for (Map.Entry<Integer, String> game : getAllGames().entrySet())
            if (compareGameNames(game.getValue(), searchTerm))
                return game.getKey();

        return -1;
    }

    /**
     * Compares two strings (game names) with equals method of class String -> later comparing with tolerance depending
     * on string lengths
     *
     * @param game1 Name of first game to check
     * @param game2 Name of second game to check
     * @return Returns true, if the game names are equal or mostly equal
     */
    protected boolean compareGameNames(String game1, String game2) {
        return game1.equals(game2);
    }

    /**
     * Removes HTML tags from passed string
     *
     * @param input Input to remove HTML tags from
     * @return Return the passed input without HTML tags
     */
    protected String removeHTMLTags(String input) {
        String regex = "<.*?>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.replaceAll("");
    }

}
