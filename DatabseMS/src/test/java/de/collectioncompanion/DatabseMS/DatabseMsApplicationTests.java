package de.collectioncompanion.DatabseMS;

import de.collectioncompanion.DatabseMS.data_files.Sammlung;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@SpringBootTest
class DatabseMsApplicationTests {

    @Test
    void contextLoads() {
    }

    @Nested
    class SammlungTest {

        private final List<String> categories = List.of("game", "movie", "series", "comics");
        private final List<String> visibilities = List.of("Privat", "Freunde", "Öffentlich");

        private Map<String, Sammlung> generateAllPossibleSammlungen(String sammlungName, List<String> categories, List<String> visibilities, List<String> collectionIds) {
            Map<String, Sammlung> sammlungen = new TreeMap<>();
            String collectionIDsString;

            if (collectionIds.isEmpty())
                collectionIDsString = "[]";
            else
                collectionIDsString = "[ \'" + String.join("\', \'", collectionIds) + "\' ]";

            for (String category : categories)
                for (String visibility : visibilities)
                    sammlungen.put("{ \'name\': \'" + sammlungName + "\', \'visibility\': \'" + visibility
                                    + "\', \'category\': \'" + category + "\', \'collectionID\': " + collectionIDsString + " }",
                            new Sammlung(sammlungName, visibility, category, new LinkedList<>(collectionIds)));

            return sammlungen;
        }

        /**
         * Tests, "toJSON()" method with a Sammlung without any collections/search results
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
         * Tests, "toJSON()" method with a Sammlung with collections/search results
         */
        @Test
        void testToJSON2() {
            for (Map.Entry<String, Sammlung> entry : generateAllPossibleSammlungen("test collection",
                    categories, visibilities, List.of("123", "456", "789")).entrySet()) {
                Sammlung result = entry.getValue();
                String expected = entry.getKey();
                assert entry.getValue().toJSON().equals(expected) : "\nResult:\n" + result.toJSON() + "\n\nExpected:\n" + expected;
            }
        }

    }

}
