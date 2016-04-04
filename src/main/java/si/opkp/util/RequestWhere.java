package si.opkp.util;

import com.moybl.restql.ast.AstNode;

public class RequestWhere {

	private AstNode condition;

	public RequestWhere(AstNode condition) {
		this.condition = condition;
	}

	public AstNode getCondition() {
		return condition;
	}

}
