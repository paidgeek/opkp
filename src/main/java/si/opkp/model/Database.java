package si.opkp.model;

import com.moybl.restql.ast.AstNode;

import java.util.Map;

import si.opkp.query.SelectOperation;
import si.opkp.util.Graph;

public interface Database {

	Graph<String, AstNode> getDataGraph();

	Map<String, NodeDefinition> getNodes();

	Map<String, FunctionDefinition> getFunctions();

	NodeResult query(SelectOperation selectOperation);

	NodeResult callFunction(String function, Object... params);

}
