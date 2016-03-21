package si.opkp.query;

public class QueryFactory {

	public static InsertBuilder insert() {
		return new SQLInsertBuilder();
	}

	public static SelectBuilder select() {
		return new SQLSelectBuilder();
	}

	public static UpdateBuilder update() {
		return new SQLUpdateBuilder();
	}

	public static DeleteBuilder delete() {
		return new SQLDeleteBuilder();
	}

	public static ConditionBuilder condition() {
		return new SQLConditionBuilder();
	}

}
