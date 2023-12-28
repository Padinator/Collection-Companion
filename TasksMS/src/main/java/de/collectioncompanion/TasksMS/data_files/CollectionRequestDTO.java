package de.collectioncompanion.TasksMS.data_files;

import com.google.gson.Gson;

public record CollectionRequestDTO(CollectionRequest collectionRequest) {

    private static final Gson gson = new Gson();

    public static String toJson(CollectionRequestDTO collectionRequestDTO) {
        return gson.toJson(collectionRequestDTO);
    }

    public static CollectionRequestDTO fromJson(String collectionRequestDTO) {
        return gson.fromJson(collectionRequestDTO, CollectionRequestDTO.class);
    }

}
