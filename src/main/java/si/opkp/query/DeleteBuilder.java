package si.opkp.query;

public interface DeleteBuilder extends QueryBuilder {

	DeleteBuilder from(String table);

	DeleteBuilder where(String condition);

	DeleteBuilder where(ConditionBuilder conditionBuilder);

}
