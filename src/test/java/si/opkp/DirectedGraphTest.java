package si.opkp;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import si.opkp.util.Graph;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DirectedGraphTest {

	private Graph<String, Integer> graph;

	@Before
	public void setup() {
		graph = new Graph<>();

		graph.addDirectedEdge("A", "B", 1);
		graph.addDirectedEdge("A", "C", 1);
		graph.addDirectedEdge("D", "A", 1);
		graph.addDirectedEdge("D", "B", 1);
	}

	@Test
	public void topologicalSort() {
		assertThat(graph.topologicalSort()
							 .get(), is(Arrays.asList("B", "C", "A", "D")));
	}

}
