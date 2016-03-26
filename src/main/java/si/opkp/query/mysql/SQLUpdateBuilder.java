package si.opkp.query.mysql;

import java.util.ArrayList;
import java.util.List;

import si.opkp.query.ConditionBuilder;
import si.opkp.query.Field;
import si.opkp.query.UpdateBuilder;
import si.opkp.util.Pair;

public class SQLUpdateBuilder implements UpdateBuilder {

	private StringBuilder query;
	private List<Pair<String, Object>> delta;
	private String condition;

	@Override
	public SQLUpdateBuilder from(String model) {
		query = new StringBuilder();
		delta = new ArrayList<>();

		query.append("UPDATE ");
		query.append(model);
		query.append("\n");

		return this;
	}

	@Override
	public SQLUpdateBuilder set(Field field, Object value) {
		delta.add(new Pair<>(field.getName(), value));

		return this;
	}

	@Override
	public SQLUpdateBuilder where(ConditionBuilder conditionBuilder) {
		this.condition = conditionBuilder.build();

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

		query.append("\nWHERE ");
		query.append(condition);
		query.append("\n");

		return query.toString();
	}

}
