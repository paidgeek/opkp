package si.opkp;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;

import si.opkp.util.DirectedGraph;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;

public class DirectedGraphTest {

	private DirectedGraph<String, Integer> graph;

	@Before
	public void setup() {
		graph = new DirectedGraph<>();

		graph.addEdge("C", "A", 1);
		graph.addEdge("C", "D", 1);
		graph.addEdge("C", "B", 1);
		graph.addEdge("F", "C", 1);
		graph.addEdge("B", "F", 1);
		graph.addEdge("B", "D", 1);
		graph.addEdge("E", "B", 1);
	}

	@Test
	public void getDistance() {
		assertEquals(Optional.of(1), graph.getDistance("C", "A"));
		assertEquals(Optional.of(1), graph.getDistance("E", "B"));
		assertEquals(Optional.of(2), graph.getDistance("E", "F"));
		assertEquals(Optional.of(2), graph.getDistance("F", "D"));
		assertEquals(Optional.of(4), graph.getDistance("E", "A"));

		assertEquals(Optional.empty(), graph.getDistance("A", "C"));
		assertEquals(Optional.empty(), graph.getDistance("A", "Z"));
	}

	@Test
	public void findPath() {
		assertThat(graph.findPath("A", "A")
							 .get(), is(Arrays.asList("A")));
		assertThat(graph.findPath("C", "A")
							 .get(), is(Arrays.asList("C", "A")));
		assertThat(graph.findPath("E", "F")
							 .get(), is(Arrays.asList("E", "B", "F")));
		assertThat(graph.findPath("E", "A")
							 .get(), is(Arrays.asList("E", "B", "F", "C", "A")));

		assertThat(graph.findPath("A", "B"), is(Optional.empty()));
	}

}
