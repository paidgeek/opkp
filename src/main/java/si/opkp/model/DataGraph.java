package si.opkp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;

import si.opkp.util.Util;

public class DataGraph {

	private static DataGraph instance;
	private Map<String, Object> map;
	private Map<String, Map<String, Integer>> distances;
	private Map<String, Map<String, String>> edges;

	private DataGraph() {
		try {
			instance = this;

			HashMap<String, Object> file = (HashMap) Util.readJSONFile("relationships.json");
			map = new HashMap<>(file);

			for (String key : file.keySet()) {
				HashMap<String, Object> relatesTo = (HashMap) file.get(key);

				for (String key1 : relatesTo.keySet()) {
					String condition = (String) relatesTo.get(key1);

					if (!map.containsKey(key1)) {
						map.put(key1, new HashMap<>());
					}

					((HashMap) map.get(key1)).put(key, condition);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		DataDefinition dd = DataDefinition.getInstance();
		distances = new HashMap<>();
		edges = new HashMap<>();
		Stack<String> s = new Stack<>();

		for (String start : dd.getTables()) {
			Map<String, Integer> dst = new HashMap<>();
			Map<String, String> edg = new HashMap<>();

			dst.put(start, 0);
			s.clear();
			s.push(start);

			while (!s.isEmpty()) {
				String v = s.pop();

				getNeighbours(v)
						.stream()
						.filter(u -> !dst.containsKey(u))
						.forEach(u -> {
							edg.put(u, v);
							dst.put(u, dst.get(v) + 1);
							s.push(u);
						});
			}

			distances.put(start, dst);
			edges.put(start, edg);
		}
	}

	public static DataGraph getInstance() {
		if (instance == null) {
			synchronized (DataGraph.class) {
				if (instance == null) {
					instance = new DataGraph();

					return instance;
				}
			}
		}

		return instance;
	}

	public Optional<String> getEdge(String a, String b) {
		if (map.containsKey(a)) {
			return Optional.of((String) ((HashMap) map.get(a)).get(b));
		} else if (map.containsKey(b)) {
			return Optional.of((String) ((HashMap) map.get(b)).get(a));
		}

		return Optional.empty();
	}

	public Set<String> getNeighbours(String a) {
		Map<String, Object> m = (HashMap) map.get(a);

		if (m == null) {
			return Collections.emptySet();
		}

		Set<String> neighbours = m.keySet();

		neighbours.remove(a);

		return neighbours;
	}

	public boolean pathExists(String start, String goal) {
		return distances.get(start)
							 .containsKey(goal);
	}

	public List<String> findPath(String start, String goal) {
		if (!pathExists(start, goal)) {
			return null;
		}

		Map<String, Integer> dst = distances.get(start);
		Map<String, String> edg = edges.get(start);
		List<String> path = new ArrayList<>(8);
		String n = goal;

		while (dst.get(n) != 0) {
			path.add(0, n);
			n = edg.get(n);
		}

		path.add(0, n);

		return path;
	}

}
