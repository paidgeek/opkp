package si.opkp.query;

import java.util.HashMap;
import java.util.Map;

import si.opkp.util.Aggregate;
<<<<<<< HEAD
=======
import si.opkp.util.RequestColumn;
>>>>>>> d6e473a869f3d61d9674ecaa1e24c8f82f694e96

class SQLSelectBuilder implements SelectBuilder {

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

	private StringBuilder query;

	@Override
	public SelectBuilder expr(RequestColumn... expr) {
		query = new StringBuilder();

		query.append("SELECT ");
		for (int i = 0; i < expr.length; i++) {
			RequestColumn col = expr[i];

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

			if (i < expr.length - 1) {
				query.append(", ");
			}
		}

		query.append("\n");

		return this;
	}

	@Override
	public SQLSelectBuilder from(String table) {
		query.append("FROM ")
			  .append(table)
			  .append("\n");

		return this;
	}

	@Override
	public SQLSelectBuilder join(String table, String condition) {
		query.append("JOIN ")
			  .append(table);

		if (condition.contains("=")) {
			query.append(" ON(" + condition + ")");
		} else {
			query.append(" USING(" + condition + ")");
		}

		query.append("\n");

		return this;
	}

	@Override
	public SelectBuilder where(String condition) {
		return where(new SQLConditionBuilder().parse(condition));
	}

	@Override
	public SQLSelectBuilder where(ConditionBuilder conditionBuilder) {
		query.append("WHERE ")
			  .append(conditionBuilder.build())
			  .append("\n");

		return this;
	}

	@Override
	public SQLSelectBuilder orderBy(RequestColumn... expr) {
		query.append("ORDER BY ");

		for (int i = 0; i < expr.length; i++) {
			RequestColumn col = expr[0];
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

			if (i < expr.length - 1) {
				query.append(", ");
			}
		}

		query.append("\n");

		return this;
	}

	@Override
	public SelectBuilder groupBy(RequestColumn... expr) {
		query.append("GROUP BY ");

		for (int i = 0; i < expr.length; i++) {
			RequestColumn col = expr[0];

			query.append(col.getName());

			AGGREGATE_PREFIXES.keySet()
									.stream()
									.filter(a -> col.getAggregate() == a)
									.forEach(a -> query.append("_")
															 .append(a.toString()));

			if (i < expr.length - 1) {
				query.append(", ");
			}
		}

		query.append("\n");

		return this;
	}

	@Override
	public SelectBuilder limit(Integer... bounds) {
		query.append("LIMIT ");

		if (bounds.length == 1) {
			query.append(bounds[0]);
		} else if (bounds.length == 2) {
			query.append(bounds[0])
				  .append(", ")
				  .append(bounds[1]);
		}

		query.append("\n");

		return this;
	}

	@Override
	public String build() {
		return query.toString();
	}

}
