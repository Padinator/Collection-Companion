package de.collectioncompanion.SteamWebCrawler.ports.data_files;

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

}
