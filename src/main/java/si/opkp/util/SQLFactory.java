package si.opkp.util;

import org.apache.commons.lang.StringEscapeUtils;

public class SQLFactory {

	private SQLFactory() {
	}

	public static SQLSelectBuilder select(String... columns) {
		return new SQLSelectBuilder(String.join(", ", columns));
	}

	public static String parseQuery(String query) {
		query = query.replaceAll(",", " AND ");
		query = query.replaceAll("\\.", " OR ");
		query = query.replaceAll(":", "=");
		query = query.replaceAll("~", " LIKE ");
		query = query.replaceAll("'", "\"");

		return query;
	}

	public static class SQLSelectBuilder {

		private StringBuilder query;

		private SQLSelectBuilder(String expr) {
			query = new StringBuilder();

			query.append("SELECT ");
			query.append(expr);
			query.append("\n");
		}

		public SQLSelectBuilder from(String table) {
			query.append("FROM ");
			query.append(table);
			query.append("\n");

			return this;
		}

		public SQLSelectBuilder where(String condition) {
			query.append("WHERE ");
			query.append(parseQuery(condition));
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
			return StringEscapeUtils.escapeSql(query.toString());
		}

	}

}
