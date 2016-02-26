package opkp.controller;

import cz.jirutka.rsql.parser.ast.*;

public class SQLGenerator extends NoArgRSQLVisitorAdapter<Void> {

	private StringBuilder sqlBuilder;

	public SQLGenerator() {
		sqlBuilder = new StringBuilder();
	}

	@Override
	public Void visit(AndNode node) {
		sqlBuilder.append("(");
		node.getChildren().get(0).accept(this);
		sqlBuilder.append(" AND ");
		node.getChildren().get(1).accept(this);
		sqlBuilder.append(")");

		return null;
	}

	@Override
	public Void visit(OrNode node) {
		sqlBuilder.append("(");
		node.getChildren().get(0).accept(this);
		sqlBuilder.append(" OR ");
		node.getChildren().get(1).accept(this);
		sqlBuilder.append(")");

		return null;
	}

	@Override
	public Void visit(ComparisonNode node) {
		sqlBuilder.append(node.getSelector());
		sqlBuilder.append(node.getOperator());

		node.getArguments().forEach(arg -> sqlBuilder.append(arg));

		return null;
	}

	public String result() {
		return sqlBuilder.toString();
	}

}
