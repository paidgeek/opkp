package si.opkp.query;

import si.opkp.util.RequestColumn;

public interface SelectBuilder extends QueryBuilder {

	SelectBuilder expr(RequestColumn... expr);

	SelectBuilder from(String table);

	SelectBuilder join(String table, String condition);

	SelectBuilder where(String condition);

	SelectBuilder where(ConditionBuilder conditionBuilder);

	SelectBuilder orderBy(RequestColumn... expr);

	SelectBuilder limit(Integer... bounds);

}
