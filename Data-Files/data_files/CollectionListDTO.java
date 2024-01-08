package data_files;

import com.google.gson.Gson;
import data_files.CollectionImpl;

import java.util.ArrayList;

public class CollectionListDTO {

    private long id;
    private ArrayList<CollectionImpl> collections;
    private static final Gson gson = new Gson();

    public CollectionListDTO(long id, ArrayList<CollectionImpl> collections) {
        this.id = id;
        this.collections = collections;
    }

    public long id() {
        return this.id;
    }

    public ArrayList<CollectionImpl> collection() {
        return this.collections;
    }

    public static String toJson(CollectionListDTO collectionListDTO) {
        return gson.toJson(collectionListDTO);
    }

    public static CollectionListDTO fromJson(String collectionListDTO) {
        return (CollectionListDTO) gson.fromJson(collectionListDTO, CollectionListDTO.class);
    }

}
