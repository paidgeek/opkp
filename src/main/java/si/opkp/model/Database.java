package si.opkp.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import si.opkp.query.ConditionBuilder;
import si.opkp.util.DirectedGraph;
import si.opkp.util.Pojo;

public interface Database {

	DirectedGraph<String, ConditionBuilder> getDataGraph();

	Validator getValidator();

	Map<String, TableDefinition> getDefinitions();

	TableDefinition getDefinition(String table);

	Set<String> getTables();

	List<Pojo> queryObjects(String sql);

	List<Pojo> queryObjects(String sql, Object... args);

	Pojo queryObject(String sql);

	Pojo queryObject(String sql, Object... args);

	long update(String sql);

	long update(String sql, Object... args);

}
