package si.opkp.query;

public interface SelectBuilder extends QueryBuilder {

	SelectBuilder fields(Field... fields);

	SelectBuilder from(String model);

	SelectBuilder join(String model, ConditionBuilder conditionBuilder);

	SelectBuilder where(ConditionBuilder conditionBuilder);

	SelectBuilder sort(Field... fields);

	SelectBuilder group(Field... fields);

	SelectBuilder skip(int skip);

	SelectBuilder take(int take);

}
