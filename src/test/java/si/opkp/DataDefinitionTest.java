package si.opkp;

import com.google.common.collect.Sets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

import si.opkp.model.Database;
import si.opkp.model.FieldDefinition;
import si.opkp.model.NodeDefinition;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
		@ContextConfiguration(name = "testContext", classes = TestData.class)
})
public class DataDefinitionTest extends BaseTest {

	@Autowired
	private Database database;

	@Test
	public void dataDefinition() {
		Set<String> tables = Sets.newHashSet("a", "b", "c", "d", "e", "f");
		HashMap<String, Set<String>> pks = new HashMap<>();
		pks.put("a", Sets.newHashSet("a_id"));
		pks.put("b", Sets.newHashSet("b_id"));
		pks.put("c", Sets.newHashSet("c_id"));
		pks.put("d", Sets.newHashSet("d_id"));
		pks.put("e", Sets.newHashSet("e_id"));
		pks.put("f", Sets.newHashSet("f_id"));

		assertThat(database.getNodes()
								 .keySet(), is(tables));

		tables.forEach(tableName -> {
			NodeDefinition td = database.getNodes()
												 .get(tableName);

			assertThat(td.getIdentifiers()
							 .stream()
							 .map(FieldDefinition::getName)
							 .collect(Collectors.toSet()), is(pks.get(tableName)));
		});
	}

}
