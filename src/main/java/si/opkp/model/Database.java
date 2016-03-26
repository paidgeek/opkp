package si.opkp.model;

import java.util.List;
import java.util.Map;

import si.opkp.query.ConditionBuilder;
import si.opkp.query.SelectBuilder;
import si.opkp.util.DirectedGraph;
import si.opkp.util.Pojo;

public interface Database {

	DirectedGraph<String, ConditionBuilder> getDataGraph();

	Map<String, ModelDefinition> getModels();

	List<Pojo> queryObjects(SelectBuilder selectBuilder, Object... args);

	Pojo queryObject(SelectBuilder selectBuilder, Object... args);

	long count(SelectBuilder selectBuilder, Object... args);

	List<Pojo> callFunction(String function, Object... args);

}
