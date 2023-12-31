package data_files;

import ports.Collection;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.data.annotation.Id;

public class CollectionImpl implements Collection {

	@Id
	private String id;

    private Map<String, String> data;

    public CollectionImpl() {
        this(new TreeMap<>());
    }

    public CollectionImpl(Map<String, String> data) {
        this.data = new TreeMap<>(data); // Deep copy
    }

    public CollectionImpl(Collection collection) {
        data = new TreeMap<>(collection.getData()); // Deep copy
    }

	@Override
	public void setData(Map<String, String> data) {
		this.data = new TreeMap<>(data); // Deep copy
	}

    @Override
    public String toString() {
        return "ID: " + id + (data.isEmpty() ? "" : data.toString());
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

    public Map<String, String> getData() {
        return Collections.unmodifiableMap(new TreeMap<>(data));
    }

    public String putEntry(String key, String value) {
        return data.put(key, value);
    }

    @Override
    public String getValue(String key) {
        if (data.containsKey(key))
            return data.get(key);
        return null;
    }

    @Override
    public boolean containsKey(String key) {
        return data.containsKey(key);
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public boolean isValid() {
        return FormatDate.isValid(data.get("time_stamp"));
    }

    @Override
    public String toParams() {
        StringBuilder params = new StringBuilder("?");

        for (Map.Entry<String, String> param : data.entrySet())
            params.append(param.getKey()).append("=").append(param.getValue()).append("&");

        if (params.toString().equals("?"))
            params = new StringBuilder();

        return params.toString();
    }

	@Override
	public String toJSON() {
		StringBuilder resultJSON = new StringBuilder("{ \"id\": \"").append(id).append("\", ");

		for (Map.Entry<String, String> propValuePair : data.entrySet()) {
			String property = "\"" + propValuePair.getKey() + "\"";
			String value = propValuePair.getValue();

			if (value.startsWith("["))
				value = "[\"" + String.join("\", \"", value.substring(1, value.length() - 1).split(", ")) + "\"]";
			else
				value = "\"" + value + "\"";

			resultJSON.append(property).append(": ").append(value).append(", ");
		}

		return resultJSON.toString().substring(0, resultJSON.length() - 2) + " }";
	}

    private static class FormatDate {

        private final static long diff = 30; // Each 30 days entries will be renewed

        public static boolean isValid(String stringDate) {
            long timeStamp = Long.parseLong(stringDate);

            System.out.println(timeStamp);
            System.out.println((diff * 24 * 60 * 60 * 1000));
            System.out.println(System.currentTimeMillis());
            System.out.println(timeStamp + (diff * 24 * 60 * 60 * 1000) - System.currentTimeMillis() > 0);

            return timeStamp + (diff * 24 * 60 * 60 * 1000) - System.currentTimeMillis() > 0;
        }

    }
}
