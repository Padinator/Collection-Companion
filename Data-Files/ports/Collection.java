package ports;

import java.util.Map;

public interface Collection {

	/**
	 * Getter for property id
	 *
	 * @return the ID of the Collection
	 */
	String getId();

	/**
	 * Setter for property id
	 *
	 * @param id of the collection
	 */
	void setId(String id);

	/**
	 * Returns a deep copy of all data (data element in CollectionImpl)
	 *
	 * @return Return an unmodifiable copied Map of data element "data" in CollectionImpl
	 */
	 Map<String, String> getData();

	/**
	 * Setter for data element "data", but makes a deep copy
	 *
	 * @param data The Map to set as data element
	 */
	void setData(Map<String, String> data);

    /**
     * Sets to the key "key" the value "value" and returns the old value
     *
     * @param key The key of the entry to insert/update
     * @param value The value of the entry to insert/update
     * @return Returns the value, which was set before to the key "key", else null
     */
    String putEntry(String key, String value);

    /**
     * Returns the value to the passed key
     *
     * @param key The key to the required value
     * @return The value to the required key, if the collection contains the key, else null
     */
    String getValue(String key);

    /**
     * Returns, if the key "key" is in the collection
     *
     * @param key The key to check
     * @return Returns true, if the passed key "key" is in the collection
     */
    boolean containsKey(String key);

    /**
     * Tests if a collection is empty/has no content.
     *
     * @return Returns true, if the collection is empty/has no content
     */
    boolean isEmpty();

    /**
     * Checks, if the collection is valid = difference between today and timestamp of creating collection is not too big
     *
     * @return Returns true, if the collection is valid
     */
    boolean isValid();

    /**
     * Creates the parameters for an URI out of the collection
     *
     * @return Returns a string with values of the collection as all URI-parameters
     */
    String toParams();

	/**
     * Format a String into a valid JSON String
     *
     * @return Returns a valid JSON String
     */
    String toJSON();
}
