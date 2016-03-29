package si.opkp.query;

import si.opkp.query.mysql.SQLSelectBuilder;

public class QueryFactory {

	public static SelectBuilder select() {
		return new SQLSelectBuilder();
	}

}
