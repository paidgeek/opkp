package si.opkp.query;

import si.opkp.model.Database;
import si.opkp.query.mysql.SQLSelectBuilder;

public class QueryFactory {

	public static SelectBuilder select(Database database) {
		return new SQLSelectBuilder(database);
	}

}
