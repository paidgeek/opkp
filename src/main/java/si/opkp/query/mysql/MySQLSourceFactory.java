package si.opkp.query.mysql;

import com.google.common.base.Enums;
import com.google.common.base.Function;

import com.moybl.restql.Token;
import com.moybl.restql.ast.*;

import java.util.Iterator;

import si.opkp.query.Aggregate;

public class MySQLSourceFactory implements Visitor {

	public static Function<String, Aggregate> aggregateConverter = Enums.stringConverter(Aggregate.class);

	public static String build(AstNode root) {
		MySQLSourceFactory f = new MySQLSourceFactory();

		root.accept(f);

		return f.build();
	}

	private StringBuilder query;

	private MySQLSourceFactory() {
		query = new StringBuilder();
	}

	@Override
	public void visit(Query acceptor) {
		acceptor.getElements()
				  .forEach(element -> element.accept(this));
	}

	@Override
	public void visit(Assignment acceptor) {
		acceptor.getDestination()
				  .accept(this);
		acceptor.getSource()
				  .accept(this);
	}

	@Override
	public void visit(Literal acceptor) {
		if (acceptor.getType() == Token.STRING) {
			query.append("'");
		}

		query.append(acceptor.getValue());

		if (acceptor.getType() == Token.STRING) {
			query.append("'");
		}
	}

	@Override
	public void visit(Identifier acceptor) {
		query.append(acceptor.getName());
	}

	@Override
	public void visit(UnaryOperation acceptor) {
		acceptor.getChild()
				  .accept(this);
	}

	@Override
	public void visit(BinaryOperation acceptor) {
		query.append("(");
		acceptor.getLeft()
				  .accept(this);

		switch (acceptor.getOperator()) {
			case EQUAL:
				query.append(" = ");
				break;
			case NOT_EQUAL:
				query.append(" != ");
				break;
			case LESS:
				query.append(" < ");
				break;
			case LESS_OR_EQUAL:
				query.append(" <= ");
				break;
			case GREATER:
				query.append(" > ");
				break;
			case GREATER_OR_EQUAL:
				query.append(" >= ");
				break;
			case AND:
				query.append(" AND ");
				break;
			case OR:
				query.append(" OR ");
				break;
		}

		acceptor.getRight()
				  .accept(this);
		query.append(")");
	}

	@Override
	public void visit(Call acceptor) {
		if (acceptor.getTarget() instanceof Identifier) {
			Aggregate aggregate = aggregateConverter.apply(((Identifier) acceptor.getTarget()).getName()
																														 .toUpperCase());

			switch (aggregate) {
				case COUNT:
					query.append("COUNT(");
					break;
				case COUNT_DISTINCT:
					query.append("COUNT(DISTINCT ");
					break;
			}
			acceptor.getArguments()
					  .accept(this);
			query.append(") AS ")
				  .append(aggregate)
				  .append("_");
			acceptor.getArguments()
					  .accept(this);
		}
	}

	@Override
	public void visit(Member acceptor) {
		acceptor.getTarget()
				  .accept(this);
		query.append(".");
		acceptor.getExpression()
				  .accept(this);
	}

	@Override
	public void visit(Sequence acceptor) {
		Iterator<AstNode> i = acceptor.getElements()
												.iterator();

		while (i.hasNext()) {
			i.next()
			 .accept(this);

			if (i.hasNext()) {
				query.append(", ");
			}
		}
	}

	public String build() {
		return query.toString();
	}

}
