package si.opkp.query;

public interface SelectBuilder extends QueryBuilder {

	SelectBuilder expr(RequestColumn... expr);

	SelectBuilder from(String table);

	SelectBuilder join(String table, ConditionBuilder condition);

	SelectBuilder where(String condition);

	SelectBuilder where(ConditionBuilder conditionBuilder);

	SelectBuilder orderBy(RequestColumn... expr);

	SelectBuilder groupBy(RequestColumn... expr);

	SelectBuilder limit(Integer... bounds);

}
