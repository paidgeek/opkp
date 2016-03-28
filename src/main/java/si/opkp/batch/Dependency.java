package si.opkp.batch;

import com.moybl.restql.ast.AstNode;

public class Dependency {

	private String command;
	private AstNode condition;

	public Dependency(String command, AstNode condition) {
		this.command = command;
		this.condition = condition;
	}

	public String getCommand() {
		return command;
	}

	public AstNode getCondition() {
		return condition;
	}

}
