package si.opkp.query;

public interface UpdateBuilder extends QueryBuilder {

	UpdateBuilder from(String model);

	UpdateBuilder set(Field field, Object value);

	UpdateBuilder where(ConditionBuilder conditionBuilder);

}
