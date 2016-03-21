package si.opkp.query;

public interface InsertBuilder extends QueryBuilder {

	InsertBuilder into(String table);

	InsertBuilder value(String column, Object value);

}
