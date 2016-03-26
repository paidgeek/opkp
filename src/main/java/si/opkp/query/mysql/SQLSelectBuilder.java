package si.opkp.query.mysql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import si.opkp.query.ConditionBuilder;
import si.opkp.query.Field;
import si.opkp.query.SelectBuilder;
import si.opkp.util.Aggregate;
import si.opkp.util.Pair;

public class SQLSelectBuilder implements SelectBuilder {

	private static final Map<Aggregate, String> AGGREGATE_PREFIXES = new HashMap<Aggregate, String>() {{
		put(Aggregate.AVG, "AVG(");
		put(Aggregate.COUNT, "COUNT(");
		put(Aggregate.COUNT_DISTINCT, "COUNT(DISTINCT ");
		put(Aggregate.MAX, "MAX(");
		put(Aggregate.MIN, "MIN(");
		put(Aggregate.SUM, "SUM(");
		put(Aggregate.STD, "STD(");
		put(Aggregate.STD_SAMPLE, "STDDEV_SAMP(");
		put(Aggregate.VAR, "VAR_POP(");
		put(Aggregate.VAR_SAMPLE, "VAR_SAMP(");
	}};

	private Field[] fields;
	private String from;
	private List<Pair<String, ConditionBuilder>> joins;
	private ConditionBuilder where;
	private Field[] sort;
	private Field[] group;
	private int skip, take;

	public SQLSelectBuilder() {
		joins = new ArrayList<>();
		skip = -1;
		take = -1;
	}

	@Override
	public SelectBuilder fields(Field... fields) {
		this.fields = fields;

		return this;
	}

	@Override
	public SQLSelectBuilder from(String model) {
		this.from = model;

		return this;
	}

	@Override
	public SQLSelectBuilder join(String model, ConditionBuilder conditionBuilder) {
		joins.add(new Pair<>(model, conditionBuilder));

		return this;
	}

	@Override
	public SQLSelectBuilder where(ConditionBuilder conditionBuilder) {
		this.where = conditionBuilder;

		return this;
	}

	@Override
	public SQLSelectBuilder sort(Field... fields) {
		this.sort = fields;

		return this;
	}

	@Override
	public SelectBuilder group(Field... fields) {
		this.group = fields;

		return this;
	}

	@Override
	public SelectBuilder skip(int skip) {
		this.skip = skip;

		return this;
	}

	@Override
	public SelectBuilder take(int take) {
		this.take = take;

		return this;
	}

	@Override
	public String build() {
		StringBuilder query = new StringBuilder();

		query.append("SELECT ");
		if (fields == null || fields.length == 0) {
			query.append("*");
		} else {
			for (int i = 0; i < fields.length; i++) {
				Field col = fields[i];

				if (col.getAggregate() == Aggregate.NONE) {
					query.append(col.getName());
				} else {
					AGGREGATE_PREFIXES.keySet()
											.stream()
											.filter(a -> col.getAggregate() == a)
											.forEach(a -> query.append(AGGREGATE_PREFIXES.get(a))
																	 .append(col.getName())
																	 .append(") AS ")
																	 .append(col.getName())
																	 .append("_")
																	 .append(a));
				}

				if (i < fields.length - 1) {
					query.append(", ");
				}
			}
		}

		query.append("\n");

		query.append("FROM ")
			  .append(from)
			  .append("\n");

		for (Pair<String, ConditionBuilder> join : joins) {
			query.append("INNER JOIN ")
				  .append(join.getFirst());

			String cond = join.getSecond()
									.build();

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
				  .append(where.build())
				  .append("\n");
		}

		if (sort != null && sort.length > 0) {
			query.append("ORDER BY ");

			for (int i = 0; i < sort.length; i++) {
				Field col = sort[0];
				char prefix = col.getName()
									  .charAt(0);

				if (prefix == '-') {
					query.append(col.getName()
										 .substring(1));
				} else {
					query.append(col.getName());
				}

				AGGREGATE_PREFIXES.keySet()
										.stream()
										.filter(a -> col.getAggregate() == a)
										.forEach(a -> query.append("_")
																 .append(a.toString()));

				if (prefix == '-') {
					query.append(" DESC");
				} else {
					query.append(" ASC");
				}

				if (i < sort.length - 1) {
					query.append(", ");
				}
			}

			query.append("\n");
		}

		if (group != null && group.length > 0) {
			query.append("GROUP BY ");

			for (int i = 0; i < group.length; i++) {
				Field col = group[0];

				query.append(col.getName());

				AGGREGATE_PREFIXES.keySet()
										.stream()
										.filter(a -> col.getAggregate() == a)
										.forEach(a -> query.append("_")
																 .append(a.toString()));

				if (i < group.length - 1) {
					query.append(", ");
				}
			}

			query.append("\n");
		}

		if (skip >= 0 || take >= 0) {
			query.append("LIMIT ")
				  .append(skip)
				  .append(", ")
				  .append(take)
				  .append("\n");
		}

		return query.toString();
	}

}
