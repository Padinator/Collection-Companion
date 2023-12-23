package de.collectioncompanion.ComposerMS.data_files;

import com.google.gson.Gson;
import de.collectioncompanion.ComposerMS.ports.data_files.Collection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public record CollectionDTO(long id, Collection collection) {

    private static final Gson gson = new Gson();

    public static String toJson(CollectionDTO collectionDTO) {
        return gson.toJson(collectionDTO);
    }

    public static CollectionDTO fromJson(String collectionDTO) {
        return gson.fromJson(collectionDTO, CollectionDTO.class);
    }

}
