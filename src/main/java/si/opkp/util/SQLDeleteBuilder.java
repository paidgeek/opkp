package si.opkp.util;

public class SQLDeleteBuilder {

	private StringBuilder query;

	public SQLDeleteBuilder(String table) {
		query = new StringBuilder();

		query.append("DELETE FROM ");
		query.append(table);
		query.append("\n");
	}

	public SQLDeleteBuilder where(String condition) {
		query.append("WHERE ");
		query.append(condition);
		query.append("\n");

		return this;
	}

	public String build() {
		return query.toString();
	}

}
