package de.collectioncompanion.TasksMS.data_files;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

public record CollectionRequest(long id, String category, String searchTerm) implements Serializable {

    public String toPath() {
        return "?category=" + category + "&searchTerm=" + searchTerm + "&id=" + id;
    }

}
