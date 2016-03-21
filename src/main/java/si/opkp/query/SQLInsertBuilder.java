package si.opkp.query;

import java.util.*;

import si.opkp.util.Pair;

class SQLInsertBuilder implements InsertBuilder {

	private StringBuilder query;
	private List<Pair<String, Object>> values;

	@Override
	public SQLInsertBuilder into(String table) {
		query = new StringBuilder();
		values = new ArrayList<>();

		query.append("INSERT INTO ");
		query.append(table);

		return this;
	}

	@Override
	public SQLInsertBuilder value(String column, Object value) {
		values.add(new Pair<>(column, value));

		return this;
	}

	@Override
	public String build() {
		query.append("(");

		for (int i = 0; i < values.size(); i++) {
			String column = values.get(i)
										 .getFirst();

			query.append("`");
			query.append(column);
			query.append("`");

			if (i < values.size() - 1) {
				query.append(", ");
			}
		}

		query.append(")\nVALUES(");

		for (int i = 0; i < values.size(); i++) {
			Object value = values.get(i)
										.getSecond();

			if (value instanceof String) {
				query.append("'");
				query.append(value);
				query.append("'");
			} else {
				query.append(value);
			}

			if (i < values.size() - 1) {
				query.append(", ");
			}
		}

		query.append(")\n");

		return query.toString();
	}

}
