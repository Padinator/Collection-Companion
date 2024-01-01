package data_files;

/*
import com.google.gson.Gson;
import ports.Collection;

public class CollectionDTO {
	
	private long id;
	private Collection collection;
    private static final Gson gson = new Gson();

	public CollectionDTO(long id, Collection collection) {
		this.id = id;
		this.collection = collection;
	}

	public long id() {
		return id;
	}

	public Collection collection() {
		return collection;
	}

    public static String toJson(CollectionDTO collectionDTO) {
        return gson.toJson(collectionDTO);
    }

    public static CollectionDTO fromJson(String collectionDTO) {
        return gson.fromJson(collectionDTO, CollectionDTO.class);
    }

}
*/

import com.google.gson.Gson;
import data_files.CollectionImpl;

public class CollectionDTO {
	
	private long id;
	private CollectionImpl collectionImpl;
    private static final Gson gson = new Gson();

	public CollectionDTO(long id, CollectionImpl collectionImpl) {
		this.id = id;
		this.collectionImpl = collectionImpl;
	}

	public long id() {
		return id;
	}

	public CollectionImpl collection() {
		return collectionImpl;
	}

    public static String toJson(CollectionDTO collectionDTO) {
        return gson.toJson(collectionDTO);
    }

    public static CollectionDTO fromJson(String collectionDTO) {
        return gson.fromJson(collectionDTO, CollectionDTO.class);
    }

}
