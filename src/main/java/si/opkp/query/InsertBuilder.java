package si.opkp.query;

public interface InsertBuilder extends QueryBuilder {

	InsertBuilder into(String model);

	InsertBuilder value(Field field, Object value);

}
