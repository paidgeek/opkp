package si.opkp.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RelationMap {

	private static RelationMap instance;
	private Map<String, Object> map;

	private RelationMap() {
		HashMap<String, Object> file = null;

		try {
			instance = this;

			file = (HashMap) Util.readJSONFile("classpath:relationships.json");
			map = new HashMap<>(file);

			for (String key : file.keySet()) {
				HashMap<String, Object> relatesTo = (HashMap) file.get(key);

				for (String key1 : relatesTo.keySet()) {
					String condition = (String) relatesTo.get(key1);

					if (map.containsKey(key1)) {
						((HashMap) map.get(key1)).put(key, condition);
					} else {
						map.put(key1, new HashMap<>());
						((HashMap) map.get(key1)).put(key, condition);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static RelationMap getInstance() {
		if (instance == null) {
			synchronized (RelationMap.class) {
				if (instance == null) {
					instance = new RelationMap();

					return instance;
				}
			}
		}

		return instance;
	}

	public String getEdge(String a, String b) {
		if (map.containsKey(a)) {
			return (String) ((HashMap) map.get(a)).get(b);
		} else if (map.containsKey(b)) {
			return (String) ((HashMap) map.get(b)).get(a);
		}

		return null;
	}

	public Set<String> getNeighbours(String a) {
		Map<String, Object> m = (HashMap) map.get(a);

		if (m == null) {
			return Collections.emptySet();
		}

		return m.keySet();
	}

}
