package de.collectioncompanion.DatabseMS.ports.data_files;

/*
 * Share this class???
 */
public interface Collection {

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
}
