package de.collectioncompanion.DatabseMS.data_files;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Sammlung {

    private String name, visibility, category;
    private List<String> collectionIds;

    public String toJSON() {
        StringBuilder sb = new StringBuilder("{ ");

        sb.append("\"name\": ").append("\"" + name + "\", ");
        sb.append("\"visibility\": ").append("\"" + visibility + "\", ");
        sb.append("\"category\": ").append("\"" + category + "\", ");

        if (collectionIds.isEmpty())
            sb.append("\"collectionID\": []");
        else
            sb.append("\"collectionID\": [ \"").append(String.join("\", \"", collectionIds)).append("\" ]");

        return sb.append(" }").toString();
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Visibility: " + visibility + ", Category:" + category + ", CollectionIds: "
                + collectionIds;
    }
}
