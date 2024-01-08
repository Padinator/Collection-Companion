package data_files;

import ports.Collection;
import ports.CollectionFormatter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameCollectionFormatter implements CollectionFormatter {

    public enum AdditionlAttributes {
        DEVELOPERS;
    }

    private static final TreeMap<String, String> properties = new TreeMap<>(
		Stream.of(
			new AbstractMap.SimpleEntry<>("title", "title"),
			new AbstractMap.SimpleEntry<>("main_img", "main_img"),
			new AbstractMap.SimpleEntry<>("short_description", "short_description"),
			new AbstractMap.SimpleEntry<>("detailed_description", "detailed_description"),
			new AbstractMap.SimpleEntry<>("required_age", "required_age"),
			new AbstractMap.SimpleEntry<>("languages", "languages"), // e.g.: [language_1, language_2, ...]
			new AbstractMap.SimpleEntry<>("genres", "genres"), // e.g.: [genre_1, genre_2, ...]
			new AbstractMap.SimpleEntry<>("available_platforms", "available_platforms"), // e.g.: [platform_1, platform_2, ...]
			new AbstractMap.SimpleEntry<>("necessary_processor", "necessary_processor"),
			new AbstractMap.SimpleEntry<>("necessary_ram", "necessary_ram"),
			new AbstractMap.SimpleEntry<>("necessary_memory", "necessary_memory"),
			new AbstractMap.SimpleEntry<>("necessary_desktop_resolution", "necessary_desktop_resolution"))
		.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
	);

    private static final TreeMap<String, String> optionalProperties = new TreeMap<>(
		Stream.of(
			new AbstractMap.SimpleEntry<>("price", "PRICE"),
			new AbstractMap.SimpleEntry<>("developers", "DEVELOPERS"))
		.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
	);

    @Override
    public String getPropertyName(String key) {
        return properties.get(key);
    }
	
    @Override
    public String getOptionalPropertyName(String key) {
        return optionalProperties.get(key);
    }

    @Override
    public boolean checkCollectionFormat(Collection collection) {
        for (String property : properties.values())
            if (!collection.containsKey(property)) // Return null, if a property is missing
                return false;

        return true;
    }

}
