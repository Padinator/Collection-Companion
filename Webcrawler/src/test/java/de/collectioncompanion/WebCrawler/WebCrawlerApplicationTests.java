package de.collectioncompanion.WebCrawler;

import data_files.CollectionImpl;
import de.collectioncompanion.WebCrawler.services.outbound.RAWGioOutImpl;
import de.collectioncompanion.WebCrawler.services.outbound.SteamAPIImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ports.Collection;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.collectioncompanion.WebCrawler.services.inbound.RestOutImpl.merge;

@SpringBootTest
class WebCrawlerApplicationTests {

    @Test
    void contextLoads() {
    }

    @Nested
    class RestOutImplTest {

        @Test
        void testMerge1() {
            List<Collection> results = merge(List.of(
                    new CollectionImpl(Stream.of(new AbstractMap.SimpleEntry<>("title", "title1"))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))),
                    new CollectionImpl(Stream.of(new AbstractMap.SimpleEntry<>("title", "title1"),
                                    new AbstractMap.SimpleEntry<>("name", "name123"))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))),
                    new CollectionImpl(Stream.of(new AbstractMap.SimpleEntry<>("title", "title2"),
                                    new AbstractMap.SimpleEntry<>("property", "value"))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))),
                    new CollectionImpl(Stream.of(new AbstractMap.SimpleEntry<>("title", "title12"),
                                    new AbstractMap.SimpleEntry<>("prop", "val"))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))),
                    new CollectionImpl(Stream.of(new AbstractMap.SimpleEntry<>("title", "title123"))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)))
                    , new CollectionImpl(Stream.of(new AbstractMap.SimpleEntry<>("title", "abcdefghi"))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)))
            ));

            List<Collection> expected = List.of(
                    new CollectionImpl(Stream.of(new AbstractMap.SimpleEntry<>("title", "title1"),
                                    new AbstractMap.SimpleEntry<>("name", "name123"),
                                    new AbstractMap.SimpleEntry<>("property", "value"),
                                    new AbstractMap.SimpleEntry<>("prop", "val"))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))),
                    new CollectionImpl(Stream.of(new AbstractMap.SimpleEntry<>("title", "title123"))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))),
                    new CollectionImpl(Stream.of(new AbstractMap.SimpleEntry<>("title", "abcdefghi"))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)))
            );

            assert expected.size() == results.size() : "Size1: " + expected.size() + ", Size2: " + results.size();

            for (int i = 0; i < expected.size(); ++i)
                assert expected.get(i).toString().equals(results.get(i).toString()) : "Expected[" + i + "]: "
                        + expected.get(i) + ", Results: [" + i + "]: " + results.get(i);
        }

    }

    @Nested
    class SteamAPIImplTest {

        String shortDescription = "Witness the journey of an officer in the homicide department from ordinary and invariably quiet life to an unknown world. Passengers of Execution is an independent game whose main theme is adventure/puzzle, although it contains many mechanics.";
        String detailedDescription = "Passengers of Execution is an independent game whose main theme is adventure/puzzle, although it contains many mechanics. It consists of maps that move along a linear line, designed in a way that you players' sense of discovery and curiosity of seeing new worlds. Sometimes you have to find the cause of death of a corpse, the shoe of a beggar, the exit of a giant maze and sometimes you have to make your hands talk.Dozens of section designs with different themes and tones.Dynamic game mechanics that are constantly changing.Exciting character designs.A curious story, far from cliché and cheap tricks.Musics that changes depending on the feeling you want to be given.Level designs designed using all angles mechanically 2d / 3d/2.5 d/isometric / perspective.Witness the journey of an officer in the homicide department from ordinary and invariably quiet life to an unknown world. Abush's soul is trapped in the software of a computer genius, he must find an exit before his body decays.Meet artificial intelligences and characters trapped in a purgatory like themselves in this adventure.Pass through the strange and exceptional level designs successfullySometimes things go wrong! Who knows, maybe one day you'll be stuck between life and death because of the stupidity of a cyber hacker. Do you think I'm kidding?";
        private final Map<String, String> data = new TreeMap<>(
                Stream.of(
                                new AbstractMap.SimpleEntry<>("title", "Passengers Of Execution"),
                                new AbstractMap.SimpleEntry<>("main_img", "https://cdn.akamai.steamstatic.com/steam/apps/1551830/header.jpg?t=1695025635"),
                                new AbstractMap.SimpleEntry<>("short_description", shortDescription),
                                new AbstractMap.SimpleEntry<>("detailed_description", detailedDescription),
                                new AbstractMap.SimpleEntry<>("required_age", "0"),
                                new AbstractMap.SimpleEntry<>("price", "0,67€"),
                                new AbstractMap.SimpleEntry<>("languages", "[English, Turkish]"),
                                new AbstractMap.SimpleEntry<>("genres", "[Adventure, Casual]"),
                                new AbstractMap.SimpleEntry<>("available_platforms", "OS *: Microsoft® Windows® 7 / 8 / 10 (32-bit/64-bit)"),
                                new AbstractMap.SimpleEntry<>("necessary_processor", "Processor: 2.0 GHz or faster processor"),
                                new AbstractMap.SimpleEntry<>("necessary_ram", "Memory: 4 GB RAM"),
                                new AbstractMap.SimpleEntry<>("necessary_memory", "Graphics: 1366 x 768 pixels or higher desktop resolution"),
                                new AbstractMap.SimpleEntry<>("necessary_desktop_resolution", "DirectX: Version 9.0"))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
        );

        /**
         * Test formatting a collection string
         */
        @Test
        void testFormatCollectionData() {
            String body = "{\"1551830\":{\"success\":true,\"data\":{\"type\":\"game\",\"name\":\"Passengers Of Execution\",\"steam_appid\":1551830,\"required_age\":0,\"is_free\":false,\"detailed_description\":\"<img src=\\\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/extras\\/Artboard_1.png?t=1695025635\\\" \\/><br>Passengers of Execution is an independent game whose main theme is adventure\\/puzzle, although it contains many mechanics. It consists of maps that move along a linear line, designed in a way that you players' sense of discovery and curiosity of seeing new worlds. Sometimes you have to find the cause of death of a corpse, the shoe of a beggar, the exit of a giant maze and sometimes you have to make your hands talk.<br><br><br><ul class=\\\"bb_ul\\\"><li>Dozens of section designs with different themes and tones.<\\/li><\\/ul><ul class=\\\"bb_ul\\\"><li>Dynamic game mechanics that are constantly changing.<\\/li><\\/ul><ul class=\\\"bb_ul\\\"><li>Exciting character designs.<\\/li><\\/ul><ul class=\\\"bb_ul\\\"><li>A curious story, far from cliché and cheap tricks.<\\/li><\\/ul><ul class=\\\"bb_ul\\\"><li>Musics that changes depending on the feeling you want to be given.<\\/li><\\/ul><ul class=\\\"bb_ul\\\"><li>Level designs designed using all angles mechanically 2d \\/ 3d\\/2.5 d\\/isometric \\/ perspective.<\\/li><\\/ul><br><img src=\\\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/extras\\/Artboard_1_copy_3.png?t=1695025635\\\" \\/><br><strong>Witness the journey of an officer in the homicide department from ordinary and invariably quiet life to an unknown world. Abush's soul is trapped in the software of a computer genius, he must find an exit before his body decays.<\\/strong><br><br><img src=\\\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/extras\\/Untitled-2.png?t=1695025635\\\" \\/><br><strong>Meet artificial intelligences and characters trapped in a purgatory like themselves in this adventure.<\\/strong><br><br><img src=\\\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/extras\\/Artboard_1_copy.png?t=1695025635\\\" \\/><br><strong>Pass through the strange and exceptional level designs successfully<\\/strong><br><br>Sometimes things go wrong! Who knows, maybe one day you'll be stuck between life and death because of the stupidity of a cyber hacker. <strong>Do you think I'm kidding?<\\/strong>\",\"about_the_game\":\"<img src=\\\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/extras\\/Artboard_1.png?t=1695025635\\\" \\/><br>Passengers of Execution is an independent game whose main theme is adventure\\/puzzle, although it contains many mechanics. It consists of maps that move along a linear line, designed in a way that you players' sense of discovery and curiosity of seeing new worlds. Sometimes you have to find the cause of death of a corpse, the shoe of a beggar, the exit of a giant maze and sometimes you have to make your hands talk.<br><br><br><ul class=\\\"bb_ul\\\"><li>Dozens of section designs with different themes and tones.<\\/li><\\/ul><ul class=\\\"bb_ul\\\"><li>Dynamic game mechanics that are constantly changing.<\\/li><\\/ul><ul class=\\\"bb_ul\\\"><li>Exciting character designs.<\\/li><\\/ul><ul class=\\\"bb_ul\\\"><li>A curious story, far from cliché and cheap tricks.<\\/li><\\/ul><ul class=\\\"bb_ul\\\"><li>Musics that changes depending on the feeling you want to be given.<\\/li><\\/ul><ul class=\\\"bb_ul\\\"><li>Level designs designed using all angles mechanically 2d \\/ 3d\\/2.5 d\\/isometric \\/ perspective.<\\/li><\\/ul><br><img src=\\\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/extras\\/Artboard_1_copy_3.png?t=1695025635\\\" \\/><br><strong>Witness the journey of an officer in the homicide department from ordinary and invariably quiet life to an unknown world. Abush's soul is trapped in the software of a computer genius, he must find an exit before his body decays.<\\/strong><br><br><img src=\\\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/extras\\/Untitled-2.png?t=1695025635\\\" \\/><br><strong>Meet artificial intelligences and characters trapped in a purgatory like themselves in this adventure.<\\/strong><br><br><img src=\\\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/extras\\/Artboard_1_copy.png?t=1695025635\\\" \\/><br><strong>Pass through the strange and exceptional level designs successfully<\\/strong><br><br>Sometimes things go wrong! Who knows, maybe one day you'll be stuck between life and death because of the stupidity of a cyber hacker. <strong>Do you think I'm kidding?<\\/strong>\",\"short_description\":\"Witness the journey of an officer in the homicide department from ordinary and invariably quiet life to an unknown world. Passengers of Execution is an independent game whose main theme is adventure\\/puzzle, although it contains many mechanics.\",\"supported_languages\":\"English, Turkish\",\"header_image\":\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/header.jpg?t=1695025635\",\"capsule_image\":\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/capsule_231x87.jpg?t=1695025635\",\"capsule_imagev5\":\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/capsule_184x69.jpg?t=1695025635\",\"website\":\"https:\\/\\/www.parterdizayn.com\",\"pc_requirements\":{\"minimum\":\"<strong>Mindestanforderungen:<\\/strong><br><ul class=\\\"bb_ul\\\"><li><strong>OS *:<\\/strong> Microsoft® Windows® 7 \\/ 8 \\/ 10 (32-bit\\/64-bit)<br><\\/li><li><strong>Processor:<\\/strong> 2.0 GHz or faster processor<br><\\/li><li><strong>Memory:<\\/strong> 4 GB RAM<br><\\/li><li><strong>Graphics:<\\/strong> 1366 x 768 pixels or higher desktop resolution<br><\\/li><li><strong>DirectX:<\\/strong> Version 9.0<br><\\/li><li><strong>Speicherplatz:<\\/strong> 400 MB verfügbarer Speicherplatz<\\/li><\\/ul>\",\"recommended\":\"<strong>Empfohlen:<\\/strong><br><ul class=\\\"bb_ul\\\"><li><strong>OS *:<\\/strong> Microsoft® Windows® 7 \\/ 8 \\/ 10 (32-bit\\/64-bit)<br><\\/li><li><strong>Processor:<\\/strong> 2.0 GHz or faster processor<br><\\/li><li><strong>Memory:<\\/strong> 8 GB RAM<br><\\/li><li><strong>Graphics:<\\/strong> 1920 x 1080 pixels or higher desktop resolution<br><\\/li><li><strong>DirectX:<\\/strong> Version 11<br><\\/li><li><strong>Speicherplatz:<\\/strong> 2000 MB verfügbarer Speicherplatz<\\/li><\\/ul>\"},\"mac_requirements\":[],\"linux_requirements\":[],\"developers\":[\"Mustafa CELIK\"],\"publishers\":[\"Parter Dizayn\"],\"price_overview\":{\"currency\":\"EUR\",\"initial\":399,\"final\":67,\"discount_percent\":83,\"initial_formatted\":\"3,99€\",\"final_formatted\":\"0,67€\"},\"packages\":[547986],\"package_groups\":[{\"name\":\"default\",\"title\":\"Passengers Of Execution kaufen\",\"description\":\"\",\"selection_text\":\"Kaufoption auswählen\",\"save_text\":\"\",\"display_type\":0,\"is_recurring_subscription\":\"false\",\"subs\":[{\"packageid\":547986,\"percent_savings_text\":\"-83% \",\"percent_savings\":0,\"option_text\":\"Passengers Of Execution - <span class=\\\"discount_original_price\\\">3,99€<\\/span> 0,67€\",\"option_description\":\"\",\"can_get_free_license\":\"0\",\"is_free_license\":false,\"price_in_cents_with_discount\":67}]}],\"platforms\":{\"windows\":true,\"mac\":false,\"linux\":false},\"categories\":[{\"id\":2,\"description\":\"Einzelspieler\"},{\"id\":22,\"description\":\"Steam-Errungenschaften\"},{\"id\":23,\"description\":\"Steam Cloud\"}],\"genres\":[{\"id\":\"25\",\"description\":\"Adventure\"},{\"id\":\"4\",\"description\":\"Casual\"}],\"screenshots\":[{\"id\":0,\"path_thumbnail\":\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/ss_180e0bc8323a5d471b3958834cb6257bf3b1e379.600x338.jpg?t=1695025635\",\"path_full\":\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/ss_180e0bc8323a5d471b3958834cb6257bf3b1e379.1920x1080.jpg?t=1695025635\"},{\"id\":1,\"path_thumbnail\":\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/ss_a4aabd0a67f376260028e29a9e1677e15f75d72b.600x338.jpg?t=1695025635\",\"path_full\":\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/ss_a4aabd0a67f376260028e29a9e1677e15f75d72b.1920x1080.jpg?t=1695025635\"},{\"id\":2,\"path_thumbnail\":\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/ss_e2555876cdb6a3f27e29f9ba0d1703f4b992ac37.600x338.jpg?t=1695025635\",\"path_full\":\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/ss_e2555876cdb6a3f27e29f9ba0d1703f4b992ac37.1920x1080.jpg?t=1695025635\"},{\"id\":3,\"path_thumbnail\":\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/ss_f146c887316418bb93444abc73051351364fe1a4.600x338.jpg?t=1695025635\",\"path_full\":\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/ss_f146c887316418bb93444abc73051351364fe1a4.1920x1080.jpg?t=1695025635\"},{\"id\":4,\"path_thumbnail\":\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/ss_e701827648884b357431346aef0c51aed32699d2.600x338.jpg?t=1695025635\",\"path_full\":\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/ss_e701827648884b357431346aef0c51aed32699d2.1920x1080.jpg?t=1695025635\"}],\"movies\":[{\"id\":256865126,\"name\":\"Trailer\",\"thumbnail\":\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/256865126\\/movie.293x165.jpg?t=1639504227\",\"webm\":{\"480\":\"http:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/256865126\\/movie480_vp9.webm?t=1639504227\",\"max\":\"http:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/256865126\\/movie_max_vp9.webm?t=1639504227\"},\"mp4\":{\"480\":\"http:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/256865126\\/movie480.mp4?t=1639504227\",\"max\":\"http:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/256865126\\/movie_max.mp4?t=1639504227\"},\"highlight\":true}],\"achievements\":{\"total\":3,\"highlighted\":[{\"name\":\"We're getting started\",\"path\":\"https:\\/\\/cdn.akamai.steamstatic.com\\/steamcommunity\\/public\\/images\\/apps\\/1551830\\/c5653ec1b3a4701e89c9e85eac96f2eba0df90c9.jpg\"},{\"name\":\"Another world\",\"path\":\"https:\\/\\/cdn.akamai.steamstatic.com\\/steamcommunity\\/public\\/images\\/apps\\/1551830\\/5904ba5cc7828d2d5630a3c6212ff383982018a1.jpg\"},{\"name\":\"It's up to me\",\"path\":\"https:\\/\\/cdn.akamai.steamstatic.com\\/steamcommunity\\/public\\/images\\/apps\\/1551830\\/9ccb1ad16deec735cf3f08fa9c82140be5fea37e.jpg\"}]},\"release_date\":{\"coming_soon\":false,\"date\":\"22. Dez. 2021\"},\"support_info\":{\"url\":\"https:\\/\\/www.parterdizayn.com\\/iletisim\",\"email\":\"celik12256@gmail.com\"},\"background\":\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/page_bg_generated_v6b.jpg?t=1695025635\",\"background_raw\":\"https:\\/\\/cdn.akamai.steamstatic.com\\/steam\\/apps\\/1551830\\/page.bg.jpg?t=1695025635\",\"content_descriptors\":{\"ids\":[],\"notes\":null}}}}";
            Map<String, String> result = new SteamAPIImpl().formatCollectionData(body);

            assert data.size() == result.size() : "Size1: " + data.size() + ", Size2: " + result.size();

            for (Map.Entry<String, String> entry1 : data.entrySet())
                assert result.get(entry1.getKey()).equals(entry1.getValue()) : "Entry1: " + entry1 + ", result: " + result;
        }

        /**
         * Tests requesting API
         */
        @Disabled // Makes problems with Github Actions
        @Test
        void testRequestGameSpecificAPI() {
            Collection result = new SteamAPIImpl().requestGameSpecificAPI("Passengers Of Execution", 1551830);
            Collection expected = new CollectionImpl(data);
            System.out.println(result);
            System.out.println(expected);
            System.out.println(difference(result.toString(), expected.toString()));

            assert expected.toString().equals(result.toString());
        }

    }

    @Nested
    class RAWGioIPIImplTest {

        String detailedDescription = "Passengers of Execution is an independent game whose main theme is adventure/puzzle, although it contains many mechanics. It consists of maps that move along a linear line, designed in a way that you players' sense of discovery and curiosity of seeing new worlds. Sometimes you have to find the cause of death of a corpse, the shoe of a beggar, the exit of a giant maze and sometimes you have to make your hands talk.\n" +
                "Dozens of section designs with different themes and tones.Dynamic game mechanics that are constantly changing.Exciting character designs.A curious story, far from cliché and cheap tricks.Musics that changes depending on the feeling you want to be given.Level designs designed using all angles mechanically 2d / 3d/2.5 d/isometric / perspective.\n" +
                "Witness the journey of an officer in the homicide department from ordinary and invariably quiet life to an unknown world. Abush's soul is trapped in the software of a computer genius, he must find an exit before his body decays.\n" +
                "Meet artificial intelligences and characters trapped in a purgatory like themselves in this adventure.\n" +
                "Pass through the strange and exceptional level designs successfully\n" +
                "Sometimes things go wrong! Who knows, maybe one day you'll be stuck between life and death because of the stupidity of a cyber hacker. Do you think I'm kidding?";

        private final Map<String, String> data = new TreeMap<>(
                Stream.of(
                                new AbstractMap.SimpleEntry<>("title", "Passengers Of Execution"),
                                new AbstractMap.SimpleEntry<>("main_img", "https://media.rawg.io/media/screenshots/303/30380397c26aabf342f4739db7e380de.jpg"),
                                new AbstractMap.SimpleEntry<>("short_description", "null"),
                                new AbstractMap.SimpleEntry<>("detailed_description", detailedDescription),
                                new AbstractMap.SimpleEntry<>("required_age", "null"),
                                new AbstractMap.SimpleEntry<>("price", "null"),
                                new AbstractMap.SimpleEntry<>("languages", "null"),
                                new AbstractMap.SimpleEntry<>("genres", "[Adventure, Casual]"),
                                new AbstractMap.SimpleEntry<>("available_platforms", "[null]"),
                                new AbstractMap.SimpleEntry<>("necessary_processor", "null"),
                                new AbstractMap.SimpleEntry<>("necessary_ram", "null"),
                                new AbstractMap.SimpleEntry<>("necessary_memory", "null"),
                                new AbstractMap.SimpleEntry<>("necessary_desktop_resolution", "null"),
                                new AbstractMap.SimpleEntry<>("DEVELOPERS", "[Mustafa CELIK]"))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
        );

        /**
         * For RAWG.io method "getAllGames()" is not supported
         */
        @Test
        void getAllGames() {
            Assertions.assertThrows(UnsupportedOperationException.class, () -> new RAWGioOutImpl().getAllGames());
        }

        /**
         * Test formatting a collection string
         */
        @Test
        void testFormatCollectionData() {
            String body = "{\"id\":509402,\"slug\":\"passengers-of-execution\",\"name\":\"Passengers Of Execution\",\"name_original\":\"Passengers Of Execution\",\"description\":\"<p>English translation is not available for now.\\r\\n</p>\\n<p> şimdilik sadece türkçe dil desteği var!<br/></p>\\n<p>Oynanabilir demoyu test için ufaktan yayınladım, geri dönüşler için şimdiden çok teşekkürler.</p>\",\"metacritic\":null,\"metacritic_platforms\":[],\"released\":\"2020-10-20\",\"tba\":false,\"updated\":\"2020-10-23T05:41:43\",\"background_image\":\"https://media.rawg.io/media/screenshots/303/30380397c26aabf342f4739db7e380de.jpg\",\"background_image_additional\":\"https://media.rawg.io/media/screenshots/b48/b4850c95f74dbeb9dbb71fe1a21a7ad0.jpg\",\"website\":\"\",\"rating\":0.0,\"rating_top\":0,\"ratings\":[],\"reactions\":null,\"added\":0,\"added_by_status\":null,\"playtime\":0,\"screenshots_count\":3,\"movies_count\":0,\"creators_count\":0,\"achievements_count\":0,\"parent_achievements_count\":0,\"reddit_url\":\"\",\"reddit_name\":\"\",\"reddit_description\":\"\",\"reddit_logo\":\"\",\"reddit_count\":0,\"twitch_count\":0,\"youtube_count\":0,\"reviews_text_count\":0,\"ratings_count\":0,\"suggestions_count\":46,\"alternative_names\":[],\"metacritic_url\":\"\",\"parents_count\":0,\"additions_count\":0,\"game_series_count\":0,\"user_game\":null,\"reviews_count\":0,\"community_rating\":0,\"saturated_color\":\"0f0f0f\",\"dominant_color\":\"0f0f0f\",\"parent_platforms\":[{\"platform\":{\"id\":1,\"name\":\"PC\",\"slug\":\"pc\"}}],\"platforms\":[{\"platform\":{\"id\":4,\"name\":\"PC\",\"slug\":\"pc\",\"image\":null,\"year_end\":null,\"year_start\":null,\"games_count\":523747,\"image_background\":\"https://media.rawg.io/media/games/618/618c2031a07bbff6b4f611f10b6bcdbc.jpg\"},\"released_at\":\"2020-10-20\",\"requirements\":{}}],\"stores\":[{\"id\":501419,\"url\":\"\",\"store\":{\"id\":9,\"name\":\"itch.io\",\"slug\":\"itch\",\"domain\":\"itch.io\",\"games_count\":654115,\"image_background\":\"https://media.rawg.io/media/games/f90/f90ee1a4239247a822771c40488e68c5.jpg\"}}],\"developers\":[{\"id\":215107,\"name\":\"Mustafa CELIK\",\"slug\":\"mustafa-celik\",\"games_count\":1,\"image_background\":\"https://media.rawg.io/media/screenshots/feb/feb1cdf73bf39e72ce660d3326f3a916.jpg\"}],\"genres\":[{\"id\":3,\"name\":\"Adventure\",\"slug\":\"adventure\",\"games_count\":137116,\"image_background\":\"https://media.rawg.io/media/games/d69/d69810315bd7e226ea2d21f9156af629.jpg\"},{\"id\":7,\"name\":\"Casual\",\"slug\":\"casual\",\"games_count\":97196,\"image_background\":\"https://media.rawg.io/media/games/c00/c003705c0eaed100397ae408b7b89e90.jpg\"}],\"tags\":[{\"id\":31,\"name\":\"Singleplayer\",\"slug\":\"singleplayer\",\"language\":\"eng\",\"games_count\":216354,\"image_background\":\"https://media.rawg.io/media/games/fc1/fc1307a2774506b5bd65d7e8424664a7.jpg\"},{\"id\":13,\"name\":\"Atmospheric\",\"slug\":\"atmospheric\",\"language\":\"eng\",\"games_count\":31643,\"image_background\":\"https://media.rawg.io/media/games/ee3/ee3e10193aafc3230ba1cae426967d10.jpg\"},{\"id\":45,\"name\":\"2D\",\"slug\":\"2d\",\"language\":\"eng\",\"games_count\":192217,\"image_background\":\"https://media.rawg.io/media/games/9fa/9fa63622543e5d4f6d99aa9d73b043de.jpg\"},{\"id\":122,\"name\":\"Pixel Graphics\",\"slug\":\"pixel-graphics\",\"language\":\"eng\",\"games_count\":92183,\"image_background\":\"https://media.rawg.io/media/games/f90/f90ee1a4239247a822771c40488e68c5.jpg\"},{\"id\":203,\"name\":\"Beat 'em up\",\"slug\":\"beat-em-up\",\"language\":\"eng\",\"games_count\":2887,\"image_background\":\"https://media.rawg.io/media/games/416/4164ca654a339af5be8e63cc9c480c70.jpg\"}],\"publishers\":[],\"esrb_rating\":null,\"clip\":null,\"description_raw\":\"English translation is not available for now.\\nşimdilik sadece türkçe dil desteği var!\\nOynanabilir demoyu test için ufaktan yayınladım, geri dönüşler için şimdiden çok teşekkürler.\"}";
            Map<String, String> result = new RAWGioOutImpl().formatCollectionData(body);
            Map<String, String> dataCopy = new TreeMap<>(data);
            dataCopy.put("detailed_description", "English translation is not available for now.\n" +
                    "şimdilik sadece türkçe dil desteği var!\n" +
                    "Oynanabilir demoyu test için ufaktan yayınladım, geri dönüşler için şimdiden çok teşekkürler.");

            assert dataCopy.size() == result.size() : "Size1: " + data.size() + ", Size2: " + result.size();

            for (Map.Entry<String, String> entry1 : dataCopy.entrySet())
                assert entry1.getValue().equals("null") && result.get(entry1.getKey()) == null ||
                        result.get(entry1.getKey()).equals(entry1.getValue()) : "Entry1: " + entry1 + ", result: " + result;
        }

        /**
         * Tests requesting API
         */
        @Disabled // Makes problems with GitHub Actions
        @Test
        void testRequestGameSpecificAPI() {
            Collection result = new RAWGioOutImpl().requestGameSpecificAPI("Passengers Of Execution", 42669);
            Collection expected = new CollectionImpl(data);
            System.out.println(result);
            System.out.println(expected);
            System.out.println(difference(result.toString(), expected.toString()));

            assert expected.toString().equals(result.toString());
        }

    }

    public static String difference(String str1, String str2) {
        if (str1 == null) {
            return str2;
        }
        if (str2 == null) {
            return str1;
        }
        int at = indexOfDifference(str1, str2);
        if (at == -1) {
            return "";
        }
        return str2.substring(at);
    }

    public static int indexOfDifference(CharSequence cs1, CharSequence cs2) {
        if (cs1 == cs2) {
            return -1;
        }
        if (cs1 == null || cs2 == null) {
            return 0;
        }
        int i;
        for (i = 0; i < cs1.length() && i < cs2.length(); ++i) {
            if (cs1.charAt(i) != cs2.charAt(i)) {
                break;
            }
        }
        if (i < cs2.length() || i < cs1.length()) {
            return i;
        }
        return -1;
    }

}
