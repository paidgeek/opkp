package si.opkp.query;

public interface UpdateBuilder extends QueryBuilder {

	UpdateBuilder from(String table);

	UpdateBuilder set(RequestColumn column, Object value);

	UpdateBuilder where(String condition);

	UpdateBuilder where(ConditionBuilder conditionBuilder);

}
