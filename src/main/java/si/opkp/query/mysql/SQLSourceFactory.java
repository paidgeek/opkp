package si.opkp.query.mysql;

import com.moybl.restql.Token;
import com.moybl.restql.ast.*;

import java.util.Iterator;

public class SQLSourceFactory implements Visitor {

	public static String build(AstNode root) {
		SQLSourceFactory f = new SQLSourceFactory();

		root.accept(f);

		return f.build();
	}

	private StringBuilder query;

	private SQLSourceFactory() {
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
		}

		acceptor.getRight()
				  .accept(this);
		query.append(")");
	}

	@Override
	public void visit(Call acceptor) {
		if (acceptor.getTarget() instanceof Identifier &&
				((Identifier) acceptor.getTarget()).getName()
															  .equalsIgnoreCase("COUNT")) {
			query.append("COUNT(");
			acceptor.getArguments()
					  .accept(this);
			query.append(") AS count_");
			acceptor.getArguments()
					  .accept(this);
		} else {
			acceptor.getTarget()
					  .accept(this);
			query.append("(");
			acceptor.getArguments()
					  .accept(this);
			query.append(")");
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
