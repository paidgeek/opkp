package si.opkp.util;

import java.util.*;

public class RelationMap {

	private static RelationMap instance;
	private HashMap map;

	private RelationMap() {
		try {
			instance = this;

			map = (HashMap) Util.readFile("classpath:relationships.json");
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
