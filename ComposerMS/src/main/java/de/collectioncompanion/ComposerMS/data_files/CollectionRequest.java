package de.collectioncompanion.ComposerMS.data_files;

import java.io.Serializable;

public record CollectionRequest(long id, String category, String searchTerm) implements Serializable {

    public String toPath() {
        return "?category=" + category + "&searchTerm=" + searchTerm + "&id=" + id;
    }

}
