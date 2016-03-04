package si.opkp.batch;

import java.util.*;

public class Batch {

	private List<Command> commands;

	public List<Command> getCommands() {
		return commands;
	}

	public List<Command> sortedCommands() {
		Map<String, List<String>> graph = new HashMap<>();

		commands.forEach(cmd -> {
			if (cmd.getDependencies() == null) {
				graph.put(cmd.getName(), Collections.emptyList());
			} else {
				graph.put(cmd.getName(), cmd.getDependencies());
			}
		});

		List<String> sorted = topologicalSort(graph);
		List<Command> sortedCommands = new ArrayList<>();

		sorted.forEach(name -> sortedCommands.add(commands.stream()
				.filter(cmd -> cmd.getName().equals(name))
				.findFirst()
				.get()));

		return sortedCommands;
	}

	private List<String> topologicalSort(Map<String, List<String>> graph) {
		Map<String, Boolean> used = new HashMap<>();
		List<String> sorted = new ArrayList<>();

		graph.keySet().forEach(node -> used.put(node, false));

		graph.keySet().forEach(node -> {
			if (!used.get(node)) {
				dfs(graph, used, sorted, node);
			}
		});

		//Collections.reverse(sorted);

		return sorted;
	}

	private void dfs(Map<String, List<String>> graph, Map<String, Boolean> used, List<String> sorted, String u) {
		used.put(u, true);

		graph.get(u).forEach(v -> {
			if (!used.get(v)) {
				dfs(graph, used, sorted, v);
			}
		});

		sorted.add(u);
	}

}
