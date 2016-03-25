package si.opkp;

import com.google.common.collect.Sets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Optional;

import si.opkp.model.Database;
import si.opkp.query.ConditionBuilder;
import si.opkp.util.DirectedGraph;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
		@ContextConfiguration(name = "testContext", classes = TestData.class)
})
public class DataGraphTest extends BaseTest {

	@Autowired
	private Database database;

	@Test
	public void nodes() {
		DirectedGraph<String, ConditionBuilder> dg = database.getDataGraph();

		assertThat(dg.getNodes(), is(Sets.newHashSet("a", "b", "c", "d", "e", "f")));
	}

	@Test
	public void structure() {
		DirectedGraph<String, ConditionBuilder> dg = database.getDataGraph();

		assertEquals(Optional.of(1), dg.getDistance("c", "a"));
		assertEquals(Optional.of(1), dg.getDistance("e", "b"));
		assertEquals(Optional.of(2), dg.getDistance("e", "f"));
		assertEquals(Optional.of(2), dg.getDistance("f", "d"));
		assertEquals(Optional.of(4), dg.getDistance("e", "a"));

		assertEquals(Optional.empty(), dg.getDistance("a", "c"));
		assertEquals(Optional.empty(), dg.getDistance("a", "z"));

		assertThat(dg.findPath("a", "a")
						 .get(), is(Arrays.asList("a")));
		assertThat(dg.findPath("c", "a")
						 .get(), is(Arrays.asList("c", "a")));
		assertThat(dg.findPath("e", "f")
						 .get(), is(Arrays.asList("e", "b", "f")));
		assertThat(dg.findPath("e", "a")
						 .get(), is(Arrays.asList("e", "b", "f", "c", "a")));

		assertThat(dg.findPath("a", "b"), is(Optional.empty()));
	}

}
