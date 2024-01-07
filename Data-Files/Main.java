import ports.*;
import data_files.*;
import data_files.GameCollectionFormatter;

public class Main {

	public static void main(String[] args) {
		System.out.println(123);
		CollectionImpl c;
		CollectionFormatter f;
		CollectionRequest cr;
		CollectionDTO cdto;
		CollectionRequestDTO crdto;
		CollectionListDTO cldto;

		// Leventhstein distances
		System.out.println(Levenshtein.calculateDistance("a", "b"));

		// Does not work!?!?!?
		System.out.println(GameCollectionFormatter.AdditionlAttributes.DEVELOPERS);
		CollectionFormatter gcfm = new GameCollectionFormatter();
	}

}
