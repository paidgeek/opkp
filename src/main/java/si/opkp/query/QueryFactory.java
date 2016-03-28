package si.opkp.query;

import com.moybl.restql.ast.AstNode;

import si.opkp.query.mysql.*;

public class QueryFactory {

	public static SelectBuilder select() {
		return new SQLSelectBuilder();
	}

}
