package si.opkp.util;

import com.moybl.restql.ast.AstNode;

public class RequestSort {

	private AstNode expression;

	public RequestSort(AstNode condition) {
		this.expression = condition;
	}

	public AstNode getExpression() {
		return expression;
	}

}
