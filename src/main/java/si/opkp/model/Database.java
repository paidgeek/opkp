package si.opkp.model;

import com.moybl.restql.ast.AstNode;

import java.util.List;
import java.util.Map;

import si.opkp.query.SelectBuilder;
import si.opkp.util.Graph;
import si.opkp.util.Pojo;

public interface Database {

	Graph<String, AstNode> getDataGraph();

	Map<String, ModelDefinition> getModels();

	List<Pojo> queryObjects(SelectBuilder selectBuilder, Object... args);

	Pojo queryObject(SelectBuilder selectBuilder, Object... args);

	long count(SelectBuilder selectBuilder, Object... args);

	List<Pojo> callFunction(String function, Object... args);

}
