package de.collectioncompanion.TasksMS.data_files;

import com.google.gson.Gson;
import lombok.Getter;

@Getter
public class CollectionRequestDTO {

    private CollectionRequest collectionRequest;

    public CollectionRequestDTO(CollectionRequest collectionRequest) {
        this.collectionRequest = collectionRequest;
    }

    private static final Gson gson = new Gson();

    public static String toJson(CollectionRequestDTO collectionRequestDTO) {
        return gson.toJson(collectionRequestDTO);
    }

    public static CollectionRequestDTO fromJson(String collectionRequestDTO) {
        return gson.fromJson(collectionRequestDTO, CollectionRequestDTO.class);
    }

}
