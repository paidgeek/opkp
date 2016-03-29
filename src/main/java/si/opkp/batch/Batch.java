package si.opkp.batch;

import com.fasterxml.jackson.annotation.*;

import java.util.*;

public class Batch {

	private List<Command> commands;

	@JsonCreator
	public Batch(@JsonProperty("commands") List<Command> commands) {
		this.commands = commands;

		sort();
	}

	public List<Command> getCommands() {
		return commands;
	}

	private void sort() {
		Map<String, List<Dependency>> graph = new HashMap<>();

		commands.forEach(cmd -> graph.put(cmd.getName(), cmd.getDependencies()));

		List<String> sorted = topologicalSort(graph);
		List<Command> sortedCommands = new ArrayList<>();

		sorted.forEach(name -> sortedCommands.add(commands.stream()
																		  .filter(cmd -> cmd.getName()
																								  .equals(name))
																		  .findFirst()
																		  .get()));

		commands = sortedCommands;
	}

	private List<String> topologicalSort(Map<String, List<Dependency>> graph) {
		Map<String, Boolean> visited = new HashMap<>();
		List<String> sorted = new ArrayList<>();

		graph.keySet()
			  .forEach(node -> visited.put(node, false));

		graph.keySet()
			  .forEach(node -> {
				  if (!visited.get(node)) {
					  dfs(graph, visited, sorted, node);
				  }
			  });

		return sorted;
	}

	private void dfs(Map<String, List<Dependency>> graph, Map<String, Boolean> visited, List<String> sorted, String u) {
		visited.put(u, true);

		for (Dependency dependency : graph.get(u)) {
			String v = dependency.getCommand();

			if (!visited.get(v)) {
				dfs(graph, visited, sorted, v);
			}
		}

		sorted.add(u);
	}

}