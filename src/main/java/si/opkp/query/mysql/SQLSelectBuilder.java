package si.opkp.query.mysql;

import com.moybl.restql.Token;
import com.moybl.restql.ast.*;

import java.util.ArrayList;
import java.util.List;

import si.opkp.model.Database;
import si.opkp.query.Aggregate;
import si.opkp.query.SelectBuilder;
import si.opkp.util.Pair;

public class SQLSelectBuilder implements SelectBuilder {

	private Database database;

	private Sequence fields;
	private AstNode from;
	private List<Pair<AstNode, AstNode>> joins;
	private AstNode where;
	private Sequence sort;
	private Sequence group;
	private AstNode skip, take;

	public SQLSelectBuilder(Database database) {
		this.database = database;

		joins = new ArrayList<>();
		skip = new Literal(0, Token.NUMBER);
		take = new Literal(100, Token.NUMBER);
	}

	@Override
	public SelectBuilder fields(Sequence fields) {
		this.fields = fields;

		return this;
	}

	@Override
	public SQLSelectBuilder from(AstNode model) {
		this.from = model;

		return this;
	}

	@Override
	public SQLSelectBuilder join(AstNode model, AstNode condition) {
		joins.add(new Pair<>(model, condition));

		return this;
	}

	@Override
	public SQLSelectBuilder where(AstNode condition) {
		this.where = condition;

		return this;
	}

	@Override
	public SQLSelectBuilder sort(Sequence fields) {
		this.sort = fields;

		return this;
	}

	@Override
	public SelectBuilder group(Sequence fields) {
		this.group = fields;

		return this;
	}

	@Override
	public SelectBuilder skip(AstNode skip) {
		this.skip = skip;

		return this;
	}

	@Override
	public SelectBuilder take(AstNode take) {
		this.take = take;

		return this;
	}

	@Override
	public String build() {
		StringBuilder query = new StringBuilder();

		query.append("SELECT ");
		if (fields == null || fields.getElements()
											 .size() == 0) {
			query.append("*");
		} else {
			query.append(SQLSourceFactory.build(fields));
		}

		query.append("\n");

		query.append("FROM ")
			  .append(SQLSourceFactory.build(from))
			  .append("\n");

		for (Pair<AstNode, AstNode> join : joins) {
			query.append("INNER JOIN ")
				  .append(SQLSourceFactory.build(join.getFirst()));

			String cond = SQLSourceFactory.build(join.getSecond());

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
				  .append(SQLSourceFactory.build(where))
				  .append("\n");
		}

		if (group != null && !group.getElements()
											.isEmpty()) {
			query.append("GROUP BY ")
				  .append(SQLSourceFactory.build(group))
				  .append("\n");
		}

		if (sort != null && !sort.getElements()
										 .isEmpty()) {
			query.append("ORDER BY ");

			for (AstNode sortNode : sort.getElements()) {
				if (sortNode instanceof Call) {
					Aggregate aggregate = SQLSourceFactory.aggregateConverter.apply(((Identifier) ((Call) sortNode).getTarget()).getName());

					query.append(aggregate)
						  .append("_")
						  .append(SQLSourceFactory.build(((Call) sortNode).getArguments()));
				} else if (sortNode instanceof UnaryOperation) {
					UnaryOperation sortUnary = (UnaryOperation) sortNode;

					if (sortUnary.getChild() instanceof Call) {
						Aggregate aggregate = SQLSourceFactory.aggregateConverter.apply(((Identifier) ((Call) sortUnary.getChild()).getTarget()).getName()
																																														.toUpperCase());

						query.append(aggregate)
							  .append("_")
							  .append(SQLSourceFactory.build(((Call) sortUnary.getChild()).getArguments()));
					} else {
						query.append(SQLSourceFactory.build(sortUnary.getChild()));
					}

					query.append(" DESC");

				} else {
					query.append(SQLSourceFactory.build(sortNode));
				}
			}

			query.append("\n");
		}

		if (skip != null || take != null) {
			query.append("LIMIT ")
				  .append(SQLSourceFactory.build(skip)
												  .replaceAll("\\.[0-9]*$", ""))
				  .append(", ")
				  .append(SQLSourceFactory.build(take)
												  .replaceAll("\\.[0-9]*$", ""))
				  .append("\n");
		}

		return query.toString();
	}

}
