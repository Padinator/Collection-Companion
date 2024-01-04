package data_files;

import ports.Collection;
import ports.CollectionFormatter;

import java.util.*;

public class GameCollectionFormatter implements CollectionFormatter {

    public enum AdditionlAttributes {
        DEVELOPERS;
    }

    private static final TreeMap<String, String> properties = new TreeMap<>();

    public GameCollectionFormatter() {
        properties.put("title", "title");
        properties.put("main_img", "main_img");
        properties.put("short_description", "short_description");
        properties.put("detailed_description", "detailed_description");
        properties.put("required_age", "required_age");
        properties.put("price", "price");
        properties.put("languages", "languages"); // e.g.: [language_1, language_2, ...]
        properties.put("genres", "genres"); // e.g.: [genre_1, genre_2, ...]
        properties.put("available_platforms", "available_platforms"); // e.g.: [platform_1, platform_2, ...]
        properties.put("necessary_processor", "necessary_processor");
        properties.put("necessary_ram", "necessary_ram");
        properties.put("necessary_memory", "necessary_memory");
        properties.put("necessary_desktop_resolution", "necessary_desktop_resolution");
    }

    @Override
    public String getPropertyName(String key) {
        return properties.get(key);
    }

    @Override
    public Collection checkCollectionFormat(Collection collection) {
        Collection resultCollection = new CollectionImpl();

        for (String property : properties.values()) {
            if (!collection.containsKey(property)) // Return null, if a property is missing
                return null;

            resultCollection.putEntry(property, collection.getValue(property));
        }

        return resultCollection;
    }

}
