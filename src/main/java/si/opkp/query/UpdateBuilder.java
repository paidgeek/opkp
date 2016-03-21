package si.opkp.query;

import si.opkp.util.RequestColumn;

public interface UpdateBuilder extends QueryBuilder {

	UpdateBuilder from(String table);

	UpdateBuilder set(RequestColumn column, Object value);

	UpdateBuilder where(String condition);

	UpdateBuilder where(ConditionBuilder conditionBuilder);

}
