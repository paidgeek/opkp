package si.opkp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import javax.annotation.PostConstruct;

import si.opkp.model.DataDefinition;
import si.opkp.util.Pojo;
import si.opkp.util.RelationMap;
import si.opkp.util.Util;

@RestController
@RequestMapping("v1/path")
@CrossOrigin
public class PathController {

	private class Node implements Comparable<Node> {

		String name;
		int distance;
		Node prev;

		Node(String name, int distance) {
			this.name = name;
			this.distance = distance;
		}

		@Override
		public int compareTo(Node o) {
			if (distance < o.distance) {
				return -1;
			}

			if (distance > o.distance) {
				return 1;
			}

			return 0;
		}

	}

	@RequestMapping("/{start}/{goals}")
	public ResponseEntity<Pojo> path(@PathVariable("start") String start,
												@PathVariable("goals") String goalsList) {
		List<String> goals = Util.parseStringList(goalsList);

		return perform(start, goals);
	}

	public ResponseEntity<Pojo> perform(String start, List<String> goals) {
		DataDefinition dd = DataDefinition.getInstance();
		RelationMap map = RelationMap.getInstance();
		String goal = goals.get(0);

		Map<String, Node> nodes = new HashMap<>();
		Queue<Node> q = new PriorityQueue<>();

		for (String table : dd.getTables()) {
			int dst = Integer.MAX_VALUE;

			if (table.equals(start)) {
				dst = 0;
			}

			nodes.put(table, new Node(table, dst));
			q.add(new Node(table, dst));
		}

		while (!q.isEmpty()) {
			Node u = q.remove();

			for (String v : map.getNeighbours(u.name)) {
				Node neighbor = nodes.get(v);
				int alt = u.distance + 1; // 1 -> cost from u to v

				if (alt < neighbor.distance) {
					neighbor.distance = alt;
					neighbor.prev = u;

					q = new PriorityQueue<>(q);
				}
			}
		}

		List<String> path = new ArrayList<>();
		Node u = nodes.get(goal);

		while (u != null) {
			path.add(u.name);
			u = u.prev;
		}

		path.add(start);

		return ResponseEntity.ok(null);
	}

}
