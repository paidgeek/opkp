package si.opkp.util;

import java.util.*;

public class Graph<T, S> {

	public class Edge {

		T nodeA;
		T nodeB;
		S value;

		Edge(T nodeA, T nodeB, S value) {
			this.nodeA = nodeA;
			this.nodeB = nodeB;
			this.value = value;
		}

		public S getValue() {
			return value;
		}

		public T getNodeA() {
			return nodeA;
		}

		public T getNodeB() {
			return nodeB;
		}

	}

	private Map<T, Map<T, Edge>> nodes;
	private boolean dirty;

	private Map<T, Map<T, Integer>> distances;
	private Map<T, Map<T, Edge>> edges;

	public Graph() {
		nodes = new HashMap<>();
		distances = new HashMap<>();
		edges = new HashMap<>();
		dirty = true;
	}

	public void addNode(T node) {
		nodes.put(node, new HashMap<>());

		dirty = true;
	}

	public void addDirectedEdge(T nodeA, T nodeB, S edge) {
		if (!nodes.containsKey(nodeA)) {
			nodes.put(nodeA, new HashMap<>());
		}

		if (!nodes.containsKey(nodeB)) {
			nodes.put(nodeB, new HashMap<>());
		}

		nodes.get(nodeA)
			  .put(nodeB, new Edge(nodeA, nodeB, edge));

		dirty = true;
	}

	public void addEdge(T nodeA, T nodeB, S edge) {
		addDirectedEdge(nodeA, nodeB, edge);
		addDirectedEdge(nodeB, nodeA, edge);
	}

	public Set<T> getNeighbours(T node) {
		return nodes.get(node)
						.keySet();
	}

	public Optional<Integer> getDistance(T start, T goal) {
		if (dirty) {
			calculatePaths();
		}

		if (distances.get(start) == null) {
			return Optional.empty();
		}

		return Optional.ofNullable(distances.get(start)
														.get(goal));
	}

	public Optional<Edge> getEdge(T nodeA, T nodeB) {
		if (!nodes.containsKey(nodeA)) {
			return Optional.empty();
		}

		if (!nodes.get(nodeA)
					 .containsKey(nodeB)) {
			return Optional.empty();
		}

		return Optional.ofNullable(nodes.get(nodeA)
												  .get(nodeB));
	}

	public Set<T> getNodes() {
		return nodes.keySet();
	}

	public Optional<List<T>> findPath(T start, T goal) {
		if (dirty) {
			calculatePaths();
		}

		if (!getDistance(start, goal).isPresent()) {
			return Optional.empty();
		}

		Map<T, Integer> dst = distances.get(start);
		Map<T, Edge> edg = edges.get(start);
		List<T> path = new ArrayList<>();
		T next = goal;

		while (dst.get(next) != 0) {
			path.add(0, next);
			next = edg.get(next).nodeA;
		}

		path.add(0, next);

		return Optional.of(path);
	}

	private void calculatePaths() {
		distances = new HashMap<>();
		edges = new HashMap<>();
		Stack<T> s = new Stack<>();

		for (T start : nodes.keySet()) {
			Map<T, Integer> dst = new HashMap<>();
			Map<T, Edge> edg = new HashMap<>();

			dst.put(start, 0);
			s.clear();
			s.push(start);

			while (!s.isEmpty()) {
				T v = s.pop();

				nodes.get(v)
					  .entrySet()
					  .stream()
					  .filter(eu -> !dst.containsKey(eu.getKey()))
					  .forEach(eu -> {
						  edg.put(eu.getKey(), eu.getValue());
						  dst.put(eu.getKey(), dst.get(v) + 1);

						  s.push(eu.getKey());
					  });
			}

			distances.put(start, dst);
			edges.put(start, edg);
		}

		dirty = false;
	}

	public Optional<List<T>> topologicalSort() {
		List<T> sorted = new ArrayList<>();
		Set<T> visited = new HashSet<>();

		for (T node : nodes.keySet()) {
			if (!visited.contains(node)) {
				try {
					rdfs(node, visited, sorted);
				} catch (NullPointerException e) {
					return Optional.empty();
				}
			}
		}

		return Optional.of(sorted);
	}

	private void rdfs(T u, Set<T> visited, List<T> sorted) {
		visited.add(u);

		nodes.get(u)
			  .keySet()
			  .stream()
			  .forEach(v -> {
				  if (!visited.contains(v))
					  rdfs(v, visited, sorted);
			  });

		sorted.add(u);
	}

}
