package de.collectioncompanion.ComposerMS;

import data_files.CollectionImpl;
import de.collectioncompanion.ComposerMS.adapter.outbound.OPExecCmd;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ports.Collection;

import java.util.AbstractMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
class ComposerMsApplicationTests {

    @Test
    void contextLoads() {
    }

    @Nested
    class CollectionTests {

        private final Collection emptyCollection = new CollectionImpl(new TreeMap<>());
        private final Collection filledCollection = new CollectionImpl(
                Stream.of(new AbstractMap.SimpleEntry<>("test123", "123"), new AbstractMap.SimpleEntry<>("name", "456"),
                                new AbstractMap.SimpleEntry<>("title", "789"))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
        );

        /**
         * Tests, if not valid collection is not valid
         */
        @Test
        void testInvalidTimeStamp1() {
            Map<String, String> data = new TreeMap<>();
            data.put("time_stamp", "123456789");
            Collection collection = new CollectionImpl(data);
            assert !collection.isValid() : "Collection should not be valid!";
        }

        /**
         * Tests, if valid collection is valid
         */
        @Test
        void testInvalidTimeStamp2() {
            Map<String, String> data = new TreeMap<>();
            data.put("time_stamp", String.valueOf(System.currentTimeMillis() + 10000000));
            Collection collection = new CollectionImpl(data);
            assert collection.isValid() : "Collection should be valid!";
        }

        @Test
        void testEmptyCollection() {
            assert emptyCollection.isEmpty() : "Collection should be empty";
        }

        /**
         * Tests the format of the method "toParams()" with an empty string
         */
        @Test
        void testToParams1() {
            assert emptyCollection.toParams().equals("");
        }

        /**
         * Tests the format of the method "toParams()" with a string
         */
        @Test
        void testToParams2() {
            assert filledCollection.toParams().equals("?name=456&test123=123&title=789&");
        }

        /**
         * Tests converting an empty collection to a JSON String
         */
        @Test
        void testToJSON1() {
            assert emptyCollection.toJSON().equals("{ \'id\': \'null\' }");
        }

        /**
         * Tests converting a filled collection to a JSON String
         */
        @Test
        void testToJSON2() {
            String resultJSON = "{ \'id\': \'null\', \'name\': \'456\', \'test123\': \'123\', \'title\': \'789\' }";
            System.out.println(resultJSON);
            System.out.println(filledCollection.toJSON());
            assert filledCollection.toJSON().equals(resultJSON);
        }

    }

    @Disabled
    @Nested
    class ExecCommandsOnCMDTests {

        @Test
        void testExecCMD() {
            boolean isWindows = System.getProperty("os.name")
                    .toLowerCase().startsWith("windows");
            String cmd, resultStr;

            if (isWindows) {
                cmd = "dir";
                resultStr =
                        "\n" +
                        "\n" +
                        "    Verzeichnis: C:\\Users\\Padinator\\IdeaProjects\\Collection-Companion\\KingSanta59\\Collection-Companion\\ComposerMS\n" +
                        "\n" +
                        "\n" +
                        "Mode                 LastWriteTime         Length Name\n" +
                        "----                 -------------         ------ ----\n" +
                        "d-----        06.01.2024     19:14                .idea\n" +
                        "d-----        27.12.2023     10:46                .mvn\n" +
                        "d-----        27.12.2023     10:46                src\n" +
                        "d-----        03.01.2024     12:13                target\n" +
                        "-a----        04.01.2024     21:21          11290 mvnw\n" +
                        "-a----        27.12.2023     10:46           7797 mvnw.cmd                                                                                                                                                                             \n" +
                        "-a----        04.01.2024     21:13           2736 pom.xml\n" +
                        "\n" +
                        "\n";
            } else {
                cmd = "ls";
                resultStr = "";
            }

            System.out.println(OPExecCmd.execCMD(cmd));
            assert resultStr.equals(OPExecCmd.execCMD(cmd));
        }

    }

}
