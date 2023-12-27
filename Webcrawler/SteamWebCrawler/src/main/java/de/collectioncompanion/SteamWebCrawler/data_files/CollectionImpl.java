package de.collectioncompanion.SteamWebCrawler.data_files;

import de.collectioncompanion.SteamWebCrawler.ports.data_files.Collection;

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
