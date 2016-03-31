package si.opkp.batch;

import com.fasterxml.jackson.annotation.*;

import java.util.*;

import si.opkp.util.Graph;

public class Batch {

	private List<Command> commands;

	@JsonCreator
	public Batch(@JsonProperty("commands") List<Command> commands) {
		Map<String, Command> commandNames = new HashMap<>();
		Graph<Command, Dependency> graph = new Graph<>();

		commands.forEach(cmd -> commandNames.put(cmd.getName(), cmd));
		commands.forEach(graph::addNode);

		for (Command cmd : commands) {
			for (Dependency dep : cmd.getDependencies()) {
				graph.addDirectedEdge(cmd, commandNames.get(dep.getCommand()), dep);
			}
		}

		Optional<List<Command>> sorted = graph.topologicalSort();

		if (!sorted.isPresent()) {
			throw new RuntimeException("cannot sort batch");
		}

		this.commands = sorted.get();
	}

	public List<Command> getCommands() {
		return commands;
	}

}