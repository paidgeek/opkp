package si.opkp.query.mysql;

import si.opkp.query.ConditionBuilder;
import si.opkp.query.DeleteBuilder;

public class SQLDeleteBuilder implements DeleteBuilder {

	private StringBuilder query;

	@Override
	public SQLDeleteBuilder from(String model) {
		query = new StringBuilder();

		query.append("DELETE FROM ");
		query.append(model);
		query.append("\n");

		return this;
	}

	@Override
	public SQLDeleteBuilder where(ConditionBuilder conditionBuilder) {
		query.append("WHERE ");
		query.append(conditionBuilder.build());
		query.append("\n");

		return this;
	}

	@Override
	public String build() {
		return query.toString();
	}

}
