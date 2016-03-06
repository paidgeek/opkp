package si.opkp.util;

import java.util.*;

public class SQLUpdateBuilder {

	private StringBuilder query;
	private List<Pair<String, Object>> delta;

	public SQLUpdateBuilder(String table) {
		query = new StringBuilder();
		delta = new ArrayList<>();

		query.append("UPDATE ");
		query.append(table);
		query.append("\n");
	}

	public SQLUpdateBuilder set(String column, Object value) {
		delta.add(new Pair<>(column, value));

		return this;
	}

	public SQLUpdateBuilder where(String condition) {
		query.append("WHERE ");
		query.append(condition);
		query.append("\n");

		return this;
	}

	public String build() {
		query.append("SET ");

		for (int i = 0; i < delta.size(); i++) {
			Pair<String, Object> setter = delta.get(i);

			query.append("`");
			query.append(setter.getFirst());
			query.append("` = ");

			if (setter.getSecond() instanceof String) {
				query.append("'");
				query.append(setter.getSecond());
				query.append("'");
			} else {
				query.append(setter.getSecond());
			}

			if (i < delta.size() - 1) {
				query.append(", ");
			}
		}

		return query.toString();
	}

}
