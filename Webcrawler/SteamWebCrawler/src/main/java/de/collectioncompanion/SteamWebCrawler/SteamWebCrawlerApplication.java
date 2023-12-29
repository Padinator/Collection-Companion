package de.collectioncompanion.SteamWebCrawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootApplication
public class SteamWebCrawlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SteamWebCrawlerApplication.class, args);

		String text = "<img src=\"https://cdn.akamai.steamstatic.com/steam/apps/1551830/extras/Artboard_1.png?t=1695025635\" /><br>Passengers of Execution is an independent game whose main theme is adventure/puzzle, although it contains many mechanics. It consists of maps that move along a linear line, designed in a way that you players' sense of discovery and curiosity of seeing new worlds. Sometimes you have to find the cause of death of a corpse, the shoe of a beggar, the exit of a giant maze and sometimes you have to make your hands talk.<br><br><br><ul class=\"bb_ul\"><li>Dozens of section designs with different themes and tones.</li></ul><ul class=\"bb_ul\"><li>Dynamic game mechanics that are constantly changing.</li></ul><ul class=\"bb_ul\"><li>Exciting character designs.</li></ul><ul class=\"bb_ul\"><li>A curious story, far from cliché and cheap tricks.</li></ul><ul class=\"bb_ul\"><li>Musics that changes depending on the feeling you want to be given.</li></ul><ul class=\"bb_ul\"><li>Level designs designed using all angles mechanically 2d / 3d/2.5 d/isometric / perspective.</li></ul><br><img src=\"https://cdn.akamai.steamstatic.com/steam/apps/1551830/extras/Artboard_1_copy_3.png?t=1695025635\" /><br><strong>Witness the journey of an officer in the homicide department from ordinary and invariably quiet life to an unknown world. Abush's soul is trapped in the software of a computer genius, he must find an exit before his body decays.</strong><br><br><img src=\"https://cdn.akamai.steamstatic.com/steam/apps/1551830/extras/Untitled-2.png?t=1695025635\" /><br><strong>Meet artificial intelligences and characters trapped in a purgatory like themselves in this adventure.</strong><br><br><img src=\"https://cdn.akamai.steamstatic.com/steam/apps/1551830/extras/Artboard_1_copy.png?t=1695025635\" /><br><strong>Pass through the strange and exceptional level designs successfully</strong><br><br>Sometimes things go wrong! Who knows, maybe one day you'll be stuck between life and death because of the stupidity of a cyber hacker. <strong>Do you think I'm kidding?</strong>";
		//System.out.println(removeHTMLTags(text));
	}

	public static String removeHTMLTags(String input) {
		String regex = "<.*?>";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		return matcher.replaceAll("");
	}

}
