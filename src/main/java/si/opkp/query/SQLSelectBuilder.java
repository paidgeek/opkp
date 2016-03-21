package si.opkp.query;

import si.opkp.util.RequestColumn;

class SQLSelectBuilder implements SelectBuilder {

	private StringBuilder query;

	@Override
	public SelectBuilder expr(RequestColumn... expr) {
		query = new StringBuilder();

		query.append("SELECT ");
		for (int i = 0; i < expr.length; i++) {
			RequestColumn col = expr[i];

			switch (col.getAggregate()) {
				case COUNT:
					query.append("COUNT(");
					query.append(col.getName());
					query.append(") AS ");
					query.append(String.format("%s_COUNT", col.getName()));
					break;
				default:
					query.append(col.getName());
					break;
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
		query.append("FROM ");
		query.append(table);
		query.append("\n");

		return this;
	}

	@Override
	public SQLSelectBuilder join(String table, String condition) {
		query.append("JOIN ");
		query.append(table);

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
		query.append("WHERE ");
		query.append(conditionBuilder.build());
		query.append("\n");

		return this;
	}

	@Override
	public SQLSelectBuilder orderBy(RequestColumn... columns) {
		query.append("ORDER BY ");

		for (int i = 0; i < columns.length; i++) {
			RequestColumn column = columns[0];
			char prefix = column.getName()
									  .charAt(0);

			if (prefix == '-') {
				query.append(column.getName()
										 .substring(1));
			} else {
				query.append(column.getName());
			}

			switch (column.getAggregate()) {
				case COUNT:
					query.append("_COUNT");
					break;
			}

			if (prefix == '-') {
				query.append(" DESC");
			} else {
				query.append(" ASC");
			}

			if (i < columns.length - 1) {
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
			query.append(bounds[0]);
			query.append(", ");
			query.append(bounds[1]);
		}

		query.append("\n");

		return this;
	}

	@Override
	public String build() {
		return query.toString();
	}

}
