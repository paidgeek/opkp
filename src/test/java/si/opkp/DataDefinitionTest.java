package si.opkp;

import com.google.common.collect.Sets;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

import si.opkp.model.DataDefinition;
import si.opkp.model.FieldDefinition;
import si.opkp.model.TableDefinition;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@ContextHierarchy({
		@ContextConfiguration(name = "testContext", classes = TestData.class)
})
public class DataDefinitionTest extends BaseTest {

	@Test
	public void dataDefinition() {
		Set<String> tables = Sets.newHashSet("A", "B", "C", "D", "E");
		HashMap<String, Set<String>> pks = new HashMap<>();
		pks.put("A", Sets.newHashSet("A_ID"));
		pks.put("B", Sets.newHashSet("B_ID"));
		pks.put("C", Sets.newHashSet("C_ID"));
		pks.put("D", Sets.newHashSet("D_ID"));
		pks.put("E", Sets.newHashSet("E_ID"));

		DataDefinition dd = DataDefinition.getInstance();

		assertThat(dd.getTables(), is(tables));

		tables.forEach(tableName -> {
			TableDefinition td = dd.getDefinition(tableName);

			assertThat(td.getPrimaryKeys()
							 .stream()
							 .map(FieldDefinition::getName)
							 .collect(Collectors.toSet()), is(pks.get(tableName)));
		});
	}

}
