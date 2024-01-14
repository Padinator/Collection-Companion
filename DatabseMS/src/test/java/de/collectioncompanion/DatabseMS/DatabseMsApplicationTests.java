package de.collectioncompanion.DatabseMS;

import de.collectioncompanion.DatabseMS.data_files.Sammlung;
import de.collectioncompanion.DatabseMS.data_files.User;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class DatabseMsApplicationTests {

    @Test
    void contextLoads() {
    }

    @Nested
    class SammlungTest {

        /**
         * Tests "toJSON()" method with a Sammlung without any collections/search results
         */
        @Test
        void testToJSON1() {
            for (Map.Entry<String, Sammlung> entry : generateAllPossibleSammlungen("test collection",
                    categories, visibilities, new LinkedList<>()).entrySet()) {
                Sammlung result = entry.getValue();
                String expected = entry.getKey();
                assert entry.getValue().toJSON().equals(expected) : "\nResult:\n" + result.toJSON() + "\n\nExpected:\n" + expected;
            }
        }

        /**
         * Tests "toJSON()" method with a Sammlung with collections/search results
         */
        @Test
        void testToJSON2() {
            for (Map.Entry<String, Sammlung> entry : generateAllPossibleSammlungen("test collection",
                    categories, visibilities, List.of("123", "456", "789")).entrySet()) {
                Sammlung result = entry.getValue();
                String expected = entry.getKey();
                System.out.println(expected);
                assert entry.getValue().toJSON().equals(expected) : "\nResult:\n" + result.toJSON() + "\n\nExpected:\n" + expected;
            }
        }

    }

    @Nested
    class UserTest {

        private final String username = "admin", pwd = "pwd123", email = "no@mail.de";
        private final List<String> userFriendIds = List.of("testuser", "max", "serrr123");
        private final List<String> userFriendRequestsId = List.of("unknown", "tom");

        /**
         * Tests "toJSON()" method with a user with Sammlungen but without friends and without friend requests
         */
        @Test
        void testToJSON1() {
            Map<String, Sammlung> sammlungen = generateAllPossibleSammlungen("test collection", categories,
                    visibilities, new LinkedList<>());
            List<Sammlung> expectedSammlungen = new LinkedList<>(sammlungen.values());
            String expectedUser = "{ \"username\": \"" + username + "\", \"password\": \"" + pwd + "\", \"email\": \"" + email + "\", \"sammlungen\": [ "
                    + String.join(", ", expectedSammlungen.stream().map(Sammlung::toJSON).toList())
                    + " ], \"userFriendsID\": [], \"userFriendRequestsId\": [] }";

            User user = new User(username, pwd, email, new LinkedList<>(), new LinkedList<>(), expectedSammlungen);
            assert user.toJSON().equals(expectedUser) : "\nResult:\n" + user.toJSON() + "\n\nExpected:\n" + expectedUser;
        }

        /**
         * Tests "toJSON()" method with a user with Sammlungen and friends and without friend requests
         */
        @Test
        void testToJSON2() {
            Map<String, Sammlung> sammlungen = generateAllPossibleSammlungen("test collection", categories,
                    visibilities, new LinkedList<>());
            List<Sammlung> expectedSammlungen = new LinkedList<>(sammlungen.values());
            String expectedUser = "{ \"username\": \"" + username + "\", \"password\": \"" + pwd + "\", \"email\": \"" + email + "\", \"sammlungen\": [ "
                    + String.join(", ", expectedSammlungen.stream().map(Sammlung::toJSON).toList())
                    + " ], \"userFriendsID\": [ \"" + String.join("\", \"", userFriendIds)
                    + "\" ], \"userFriendRequestsId\": [] }";

            User user = new User(username, pwd, email, userFriendIds, new LinkedList<>(), expectedSammlungen);
            assert user.toJSON().equals(expectedUser) : "\nResult:\n" + user.toJSON() + "\n\nExpected:\n" + expectedUser;
        }

        /**
         * Tests "toJSON()" method with a user without Sammlungen and without friends and without friend requests
         */
        @Test
        void testToJSON3() {
            Map<String, Sammlung> sammlungen = generateAllPossibleSammlungen("test collection", categories,
                    visibilities, new LinkedList<>());
            String expectedUser = "{ \"username\": \"" + username + "\", \"password\": \"" + pwd + "\", \"email\": \""
                    + email + "\", \"sammlungen\": [], \"userFriendsID\": [], \"userFriendRequestsId\": [] }";

            User user = new User(username, pwd, email, new LinkedList<>(), new LinkedList<>(), new LinkedList<>());
            assert user.toJSON().equals(expectedUser) : "\nResult:\n" + user.toJSON() + "\n\nExpected:\n" + expectedUser;
        }

        /**
         * Tests "toJSON()" method with a user without Sammlungen but with friends and without friend requests
         */
        @Test
        void testToJSON4() {
            Map<String, Sammlung> sammlungen = generateAllPossibleSammlungen("test collection", categories,
                    visibilities, new LinkedList<>());
            String expectedUser = "{ \"username\": \"" + username + "\", \"password\": \"" + pwd + "\", \"email\": \""
                    + email + "\", \"sammlungen\": [], \"userFriendsID\": [ \""
                    + String.join("\", \"", userFriendIds) + "\" ], \"userFriendRequestsId\": [] }";

            User user = new User(username, pwd, email, userFriendIds, new LinkedList<>(), new LinkedList<>());
            assert user.toJSON().equals(expectedUser) : "\nResult:\n" + user.toJSON() + "\n\nExpected:\n" + expectedUser;
        }

        /**
         * Tests "toJSON()" method with a user with Sammlungen but with friends and with friend requests
         */
        @Test
        void testToJSON5() {
            Map<String, Sammlung> sammlungen = generateAllPossibleSammlungen("test collection", categories,
                    visibilities, new LinkedList<>());
            List<Sammlung> expectedSammlungen = new LinkedList<>(sammlungen.values());
            String expectedUser = "{ \"username\": \"" + username + "\", \"password\": \"" + pwd + "\", \"email\": \""
                    + email + "\", \"sammlungen\": [ "
                    + String.join(", ", expectedSammlungen.stream().map(Sammlung::toJSON).toList())
                    + " ], \"userFriendsID\": [ \""
                    + String.join("\", \"", userFriendIds) + "\" ], \"userFriendRequestsId\": [ \""
                    + String.join("\", \"", userFriendRequestsId)  + "\" ] }";

            User user = new User(username, pwd, email, userFriendIds, userFriendRequestsId, expectedSammlungen);
            assert user.toJSON().equals(expectedUser) : "\nResult:\n" + user.toJSON() + "\n\nExpected:\n" + expectedUser;
        }

    }

    /*
     * Helper functions for generating Sammlungen
     */
    private final List<String> categories = List.of("game", "movie", "series", "comics");
    private final List<String> visibilities = List.of("Privat", "Freunde", "Öffentlich");

    private Map<String, Sammlung> generateAllPossibleSammlungen(String sammlungName, List<String> categories, List<String> visibilities, List<String> collectionIds) {
        Map<String, Sammlung> sammlungen = new TreeMap<>();
        String collectionIDsString;

        if (collectionIds.isEmpty())
            collectionIDsString = "[]";
        else
            collectionIDsString = "[ \"" + String.join("\", \"", collectionIds) + "\" ]";

        for (String category : categories)
            for (String visibility : visibilities)
                sammlungen.put("{ \"name\": \"" + sammlungName + "\", \"visibility\": \"" + visibility
                                + "\", \"category\": \"" + category + "\", \"collectionID\": " + collectionIDsString + " }",
                        new Sammlung(sammlungName, visibility, category, new LinkedList<>(collectionIds)));

        return sammlungen;
    }

}
