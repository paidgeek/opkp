package si.opkp.query;

class SQLDeleteBuilder implements DeleteBuilder {

	private StringBuilder query;

	@Override
	public SQLDeleteBuilder from(String table) {
		query = new StringBuilder();

		query.append("DELETE FROM ");
		query.append(table);
		query.append("\n");

		return this;
	}

	@Override
	public DeleteBuilder where(String condition) {
		return where(new SQLConditionBuilder().parse(condition));
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
