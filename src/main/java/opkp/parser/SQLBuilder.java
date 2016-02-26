package opkp.parser;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.*;

import java.util.HashMap;
import java.util.Map;

public class SQLBuilder extends NoArgRSQLVisitorAdapter<Void> {

	private static Map<String, String> operatorMap = new HashMap<String, String>() {
		{
			put("==", "=");
			put("!=", "!=");
			put("=lt=", "<");
			put("=le=", "<=");
			put("=gt=", ">");
			put("=ge=", ">=");
			put("=in=", "IN");
			put("=out=", "NOT IN");
		}
	};

	private StringBuilder stringBuilder;

	public SQLBuilder() {
		stringBuilder = new StringBuilder();
	}

	public SQLBuilder select() {
		stringBuilder.append("SELECT * ");

		return this;
	}

	public SQLBuilder from(String table) {
		stringBuilder.append("FROM " + table + " ");

		return this;
	}

	public SQLBuilder where(String condition) {
		stringBuilder.append("WHERE ");

		new RSQLParser().parse(condition).accept(this);

		return this;
	}

	@Override
	public Void visit(AndNode node) {
		stringBuilder.append("(");
		node.getChildren().get(0).accept(this);
		stringBuilder.append(" AND ");
		node.getChildren().get(1).accept(this);
		stringBuilder.append(")");

		return null;
	}

	@Override
	public Void visit(OrNode node) {
		stringBuilder.append("(");
		node.getChildren().get(0).accept(this);
		stringBuilder.append(" OR ");
		node.getChildren().get(1).accept(this);
		stringBuilder.append(")");

		return null;
	}

	@Override
	public Void visit(ComparisonNode node) {
		stringBuilder.append(node.getSelector());

		String op = operatorMap.get(node.getOperator().getSymbol());
		stringBuilder.append(" " + op + " ");

		stringBuilder.append(node.getArguments().get(0));

		return null;
	}

	public String build() {
		stringBuilder.append(";");

		return stringBuilder.toString();
	}

}
