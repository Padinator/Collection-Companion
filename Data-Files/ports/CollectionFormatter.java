package ports;

import ports.Collection;
import data_files.Levenshtein;

public interface CollectionFormatter {

    /**
     * Returns to a passed key/property the property name
     *
     * @param property The property to get the name from
     * @return The name of the property, if the property exists, else null
     */
    String getPropertyName(String property);

    /**
     * Checks, if the passed collection has the correct format
     *
     * @param collection The collection to check
     * @return true, if the passed collection has the correct format, else false
     */
    boolean checkCollectionFormat(Collection collection);

	/**
     * Compares two strings (game names) with "Levenshtein Distance". If the Levenshtein Distance is
	 * lower than the allowed distance (count of missspells of a word), then game2 could be game1, else not.
     *
     * @param game1 Name of first game to check
     * @param game2 Name of second game to check
     * @return Returns true, if the game names are equal or mostly equal
     */
	static boolean compareGameNames(String game1, String game2) {
		int distance = Levenshtein.calculateDistance(game1, game2);
		int allowedDistance = Levenshtein.calculateAcceptableMistakes(game1);
		return distance <= allowedDistance;
    }

}
