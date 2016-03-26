package si.opkp.query;

public interface DeleteBuilder extends QueryBuilder {

	DeleteBuilder from(String model);

	DeleteBuilder where(ConditionBuilder conditionBuilder);

}
