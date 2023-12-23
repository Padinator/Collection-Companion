package de.collectioncompanion.ComposerMS.data_files;

import de.collectioncompanion.ComposerMS.ports.data_files.Collection;

import java.util.Arrays;
import java.util.Map;

/*
 * Share this class???
 */
public class CollectionImpl implements Collection {

    private final Map<String, String> data;

    public CollectionImpl(Map<String, String> results) {
        data = results;
    }

    @Override
    public String toString() {
        return data.isEmpty() ? "" : data.toString();
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

    private class FormatDate {

        private final static long diff = 30; // Each 30 days entries will be renewed

        public static boolean isValid(String stringDate) {
            int[] dateParts = Arrays.stream(stringDate.split(" ")).mapToInt(s -> Integer.parseInt(s)).toArray();

            if (dateParts.length != 3)
                throw new IllegalArgumentException("Cannot create a Date object with false format!\n"
                        + "expected: year month day\n"
                        + "given: " + stringDate);

            return Long.parseLong(stringDate) + (diff * 24 * 60 * 60 * 1000) - System.currentTimeMillis() > 0;
        }

    }
}