package si.opkp.query;

public interface ConditionBuilder extends QueryBuilder {

	ConditionBuilder equal(Object a, Object b);

	ConditionBuilder and();

	ConditionBuilder or();

	ConditionBuilder parse(String source);

}
