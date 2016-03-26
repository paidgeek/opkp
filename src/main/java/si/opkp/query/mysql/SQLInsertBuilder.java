package si.opkp.query.mysql;

import java.util.ArrayList;
import java.util.List;

import si.opkp.query.Field;
import si.opkp.query.InsertBuilder;
import si.opkp.util.Pair;

public class SQLInsertBuilder implements InsertBuilder {

	private StringBuilder query;
	private List<Pair<Field, Object>> values;

	@Override
	public SQLInsertBuilder into(String model) {
		query = new StringBuilder();
		values = new ArrayList<>();

		query.append("INSERT INTO ");
		query.append(model);

		return this;
	}

	@Override
	public SQLInsertBuilder value(Field field, Object value) {
		values.add(new Pair<>(field, value));

		return this;
	}

	@Override
	public String build() {
		query.append("(");

		for (int i = 0; i < values.size(); i++) {
			Field field = values.get(i)
									  .getFirst();

			query.append("`");
			query.append(field);
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
