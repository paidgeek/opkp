package si.opkp.query.mysql;

import com.moybl.restql.ast.AstNode;

import java.util.List;

import si.opkp.query.SelectOperation;
import si.opkp.query.SelectOperationBuilder;
import si.opkp.util.Graph;
import si.opkp.util.RequestField;
import si.opkp.util.RequestWhere;

public class MySQLSelectOperationBuilder implements SelectOperationBuilder {

	@Override
	public String build(SelectOperation selectOperation) {
		List<RequestField> fields = selectOperation.getFields();
		String from = selectOperation.getFrom();
		List<Graph<String, AstNode>.Edge> joins = selectOperation.getJoins();
		RequestWhere where = selectOperation.getWhere();
		long skip = selectOperation.getSkip();
		long take = selectOperation.getTake();

		StringBuilder query = new StringBuilder();

		query.append("SELECT ");

		for (int i = 0; i < fields.size(); i++) {
			RequestField field = fields.get(i);

			query.append(field.getNode())
				  .append(".")
				  .append(field.getName())
				  .append(" AS ")
				  .append("'")
				  .append(field.getNode())
				  .append(".")
				  .append(field.getName())
				  .append("'");

			if (i < fields.size() - 1) {
				query.append(", ");
			}
		}

		query.append("\n");

		query.append("FROM ")
			  .append(from)
			  .append("\n");

		for (Graph<String, AstNode>.Edge edge : joins) {
			query.append("INNER JOIN ")
				  .append(edge.getNodeB());

			String cond = MySQLSourceFactory.build(edge.getValue());

			if (cond.matches("^[a-zA-Z0-9_]*$")) {
				query.append(" USING(")
					  .append(cond)
					  .append(")");
			} else {
				query.append(" ON(")
					  .append(cond)
					  .append(")");
			}

			query.append("\n");
		}


		if (where != null) {
			query.append("WHERE ")
				  .append(MySQLSourceFactory.build(where.getCondition()))
				  .append("\n");
		}

/*
		if (group != null && !group.getElements()
											.isEmpty()) {
			query.append("GROUP BY ")
				  .append(MySQLSourceFactory.build(group))
				  .append("\n");
		}

		if (sort != null && !sort.getElements()
										 .isEmpty()) {
			query.append("ORDER BY ");

			for (AstNode sortNode : sort.getElements()) {
				if (sortNode instanceof Call) {
					Aggregate aggregate = MySQLSourceFactory.aggregateConverter.apply(((Identifier) ((Call) sortNode).getTarget()).getName());

					query.append(aggregate)
						  .append("_")
						  .append(MySQLSourceFactory.build(((Call) sortNode).getArguments()));
				} else if (sortNode instanceof UnaryOperation) {
					UnaryOperation sortUnary = (UnaryOperation) sortNode;

					if (sortUnary.getChild() instanceof Call) {
						Aggregate aggregate = MySQLSourceFactory.aggregateConverter.apply(((Identifier) ((Call) sortUnary.getChild()).getTarget()).getName()
																																														.toUpperCase());

						query.append(aggregate)
							  .append("_")
							  .append(MySQLSourceFactory.build(((Call) sortUnary.getChild()).getArguments()));
					} else {
						query.append(MySQLSourceFactory.build(sortUnary.getChild()));
					}

					query.append(" DESC");

				} else {
					query.append(MySQLSourceFactory.build(sortNode));
				}
			}

			query.append("\n");
		}
		*/

		if (skip >= 0 && take >= 0) {
			query.append("LIMIT ")
				  .append(skip)
				  .append(", ")
				  .append(take)
				  .append("\n");
		} else {
			query.append("LIMIT 50\n");
		}

		return query.toString();
	}

}
