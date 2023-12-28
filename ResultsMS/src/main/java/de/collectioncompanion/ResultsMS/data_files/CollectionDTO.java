package de.collectioncompanion.ResultsMS.data_files;

import com.google.gson.Gson;

public record CollectionDTO(long id, /*Collection */ CollectionImpl collection) {

    private static final Gson gson = new Gson();

    public static String toJson(CollectionDTO collectionDTO) {
        return gson.toJson(collectionDTO);
    }

    public static CollectionDTO fromJson(String collectionDTO) {
        return gson.fromJson(collectionDTO, CollectionDTO.class);
    }

}
