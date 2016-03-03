package si.opkp.util;

public class SQLSelectBuilder {

	private StringBuilder query;

	public SQLSelectBuilder(String... columns) {
		query = new StringBuilder();

		query.append("SELECT ");
		query.append(String.join(",", columns));
		query.append("\n");
	}

	public SQLSelectBuilder from(String table) {
		query.append("FROM ");
		query.append(table);
		query.append("\n");

		return this;
	}

	public SQLSelectBuilder join(String table, String joinType, String condition) {
		if(joinType.equals("<>")) {
			query.append("FULL JOIN ");
		} else if(joinType.equals("><")) {
			query.append("INNER JOIN ");
		} else if(joinType.equals("<")) {
			query.append("LEFT JOIN ");
		} else if(joinType.equals(">")) {
			query.append("RIGHT JOIN ");
		}

		query.append(table);

		if (condition.contains("=")) {
			query.append(" ON(" + condition + ")");
		} else {
			query.append(" USING(" + condition + ")");
		}

		query.append("\n");

		return this;
	}

	public SQLSelectBuilder where(String condition) {
		query.append("WHERE ");
		query.append(condition);
		query.append("\n");

		return this;
	}

	public SQLSelectBuilder orderByPrefixedColumns(String... columns) {
		query.append("ORDER BY ");

		for (int i = 0; i < columns.length; i++) {
			String column = columns[i];
			char prefix = column.charAt(0);

			if (prefix == '-') {
				query.append(column.substring(1));
				query.append(" ASC");
			} else {
				query.append(column);
				query.append(" DESC");
			}

			if (i < columns.length - 1) {
				query.append(", ");
			}
		}

		query.append("\n");

		return this;
	}

	public SQLSelectBuilder limit(int... bounds) {
		query.append("LIMIT ");

		if (bounds.length == 1) {
			query.append(bounds[0]);
		} else {
			query.append(bounds[0]);
			query.append(", ");
			query.append(bounds[1]);
		}

		query.append("\n");

		return this;
	}

	public String build() {
		// TODO sql injection?
		return query.toString();
	}

}
