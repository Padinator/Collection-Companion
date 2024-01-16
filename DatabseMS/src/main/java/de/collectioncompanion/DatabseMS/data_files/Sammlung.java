package de.collectioncompanion.DatabseMS.data_files;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Sammlung {

    private String name, visibility, category;
    private List<String> collectionIds;
    private List<Pair<String, Boolean>> evaluations;

    public String toJSON() {
        StringBuilder sb = new StringBuilder("{ ");

        sb.append("\"name\": ").append("\"").append(name).append("\", ");
        sb.append("\"visibility\": ").append("\"").append(visibility).append("\", ");
        sb.append("\"category\": ").append("\"").append(category).append("\", ");

        if (collectionIds.isEmpty())
            sb.append("\"collectionID\": [], ");
        else
            sb.append("\"collectionID\": [ \"").append(String.join("\", \"", collectionIds)).append("\" ], ");

        if (evaluations.isEmpty())
            sb.append("\"evaluations\": []");
        else
            sb.append("\"evaluations\": [ \"")
                    .append(String.join("\", \"", evaluations.stream()
                            .map(evaluation -> "( " + evaluation.getKey() + ", " + evaluation.getValue() + ")").toList()))
                    .append("\" ]");

        return sb.append(" }").toString();
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Visibility: " + visibility + ", Category:" + category + ", CollectionIds: "
                + collectionIds;
    }
}
