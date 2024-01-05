package ports;

import ports.Collection;

public interface CollectionFormatter {

    /**
     * Returns to a passed key/property the property name
     *
     * @param property The property to get the name from
     * @return The name of the property, if the property exists, else null
     */
    String getPropertyName(String property);

    /**
     * Formats the passed collection, so it has the correct format.
     *
     * @param collection The collection to check
     * @return Returns the formatted collection, if the passed collection can be formatted, else null
     */
    Collection checkCollectionFormat(Collection collection);

	/**
     * Compares two strings (game names) with equals method of class String -> later comparing with tolerance depending
     * on string lengths
     *
     * @param game1 Name of first game to check
     * @param game2 Name of second game to check
     * @return Returns true, if the game names are equal or mostly equal
     */
	static boolean compareGameNames(String game1, String game2) {
		return game1.equals(game2);
    }

}
