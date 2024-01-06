package de.collectioncompanion.ComposerMS;

import data_files.CollectionImpl;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ports.Collection;

import java.util.Map;
import java.util.TreeMap;

@SpringBootTest
class ComposerMsApplicationTests {

	@Test
	void contextLoads() {
	}

	@Nested
	class CollectionTests {

		@Test
		void testInvalidTimeStamp1() {
			Map<String, String> data = new TreeMap<>();
			Collection collection = new CollectionImpl(data);
		}

	}

}
