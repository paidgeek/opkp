package si.opkp.model;

import com.moybl.restql.ast.AstNode;

import java.util.Map;

import si.opkp.query.SelectBuilder;
import si.opkp.util.Graph;

public interface Database {

	Graph<String, AstNode> getDataGraph();

	Map<String, ModelDefinition> getModels();

	QueryResult queryObjects(SelectBuilder selectBuilder, Object... args);

	QueryResult callFunction(String function, Object... args);

}
