package si.opkp.query;

public interface SelectBuilder extends QueryBuilder {

	SelectBuilder expr(String... expr);

	SelectBuilder from(String table);

	SelectBuilder join(String table, String condition);

	SelectBuilder where(String condition);

	SelectBuilder where(ConditionBuilder conditionBuilder);

	SelectBuilder orderBy(String... expr);

	SelectBuilder limit(Integer... bounds);

}
