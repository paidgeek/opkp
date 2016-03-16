package si.opkp.util;

import java.util.*;

public class SQLSelectBuilder {

	private StringBuilder query;

	public SQLSelectBuilder(Iterable<? extends CharSequence> columns) {
		query = new StringBuilder();

		query.append("SELECT ");
		query.append(String.join(",", columns));
		query.append("\n");
	}

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

	public SQLSelectBuilder where(String condition) {
		query.append("WHERE ");
		query.append(condition);
		query.append("\n");

		return this;
	}

	public SQLSelectBuilder orderByPrefixedColumns(List<String> columns) {
		query.append("ORDER BY ");

		for (int i = 0; i < columns.size(); i++) {
			String column = columns.get(i);
			char prefix = column.charAt(0);

			if (prefix == '-') {
				query.append(column.substring(1));
				query.append(" DESC");
			} else {
				query.append(column);
				query.append(" ASC");
			}

			if (i < columns.size() - 1) {
				query.append(", ");
			}
		}

		query.append("\n");

		return this;
	}

	public SQLSelectBuilder limit(List<Integer> bounds) {
		query.append("LIMIT ");

		if (bounds.size() == 1) {
			query.append(bounds.get(0));
		} else {
			query.append(bounds.get(0));
			query.append(", ");
			query.append(bounds.get(1));
		}

		query.append("\n");

		return this;
	}

	public String build() {
		// TODO sql injection?
		return query.toString();
	}

}
