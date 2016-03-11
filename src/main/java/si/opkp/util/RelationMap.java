package si.opkp.util;

import java.util.*;

public class RelationMap {

	private static RelationMap instance;
	private HashMap<String, Object> map;

	private RelationMap() {
		try {
			instance = this;

			map = (HashMap) Util.readFile("classpath:relationships.json");

			for (Map.Entry<String, Object> e : map.entrySet()) {
				Map<String, String> values = (HashMap) e.getValue();

				for (Map.Entry<String, String> value : values.entrySet()) {
					if (map.containsKey(value.getKey())) {
						((HashMap) map.get(value.getKey())).put(e.getKey(), value.getValue());
					} else {
						Map<String, String> m = new HashMap<>();

						m.put(e.getKey(), value.getValue());

						map.put(value.getKey(), m);
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

}
