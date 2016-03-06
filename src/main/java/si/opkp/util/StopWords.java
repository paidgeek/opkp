package si.opkp.util;

import java.util.*;

public class StopWords {

	private static StopWords instance;
	private Map<String, Set<String>> words;

	private StopWords() {
		instance = this;

		words = new HashMap<>();
		HashMap map = (HashMap) Util.readFile("classpath:stopwords.json");

		map.entrySet().forEach(e -> {
			Map.Entry<String, List<String>> entry = (Map.Entry<String, List<String>>) e;
			Set<String> set = new HashSet<>();

			set.addAll(entry.getValue());
			words.put(entry.getKey(), set);
		});
	}

	public Set<String> getStopWords(String language) {
		return words.get(language);
	}

	public static StopWords getInstance() {
		if (instance == null) {
			synchronized (StopWords.class) {
				if (instance == null) {
					instance = new StopWords();

					return instance;
				}
			}
		}

		return instance;
	}

}
