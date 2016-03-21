package si.opkp.query;

import java.util.Arrays;

class SQLSelectBuilder implements SelectBuilder {

	private StringBuilder query;

	@Override
	public SelectBuilder expr(String... expr) {
		query = new StringBuilder();

		query.append("SELECT ");
		query.append(String.join(",", Arrays.asList(expr)));
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
	public SQLSelectBuilder orderBy(String... columns) {
		query.append("ORDER BY ");

		for (int i = 0; i < columns.length; i++) {
			String column = columns[0];
			char prefix = column.charAt(0);

			if (prefix == '-') {
				query.append(column.substring(1));
				query.append(" DESC");
			} else {
				query.append(column);
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
