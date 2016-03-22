package si.opkp.query;

import com.moybl.restql.RestQL;

import si.opkp.model.FieldDefinition;

public class SQLConditionBuilder implements ConditionBuilder {

	private StringBuilder condition;

	public SQLConditionBuilder() {
		condition = new StringBuilder();
	}

	@Override
	public SQLConditionBuilder equal(Object a, Object b) {
		if (a instanceof FieldDefinition) {
			condition.append('`');
			condition.append(((FieldDefinition) a).getName());
			condition.append('`');
		} else if (a instanceof RequestColumn) {
			condition.append('\'');
			condition.append(((RequestColumn) a).getName());
			condition.append('\'');
		} else {
			condition.append(a);
		}

		condition.append(" = ");

		if (b instanceof FieldDefinition) {
			condition.append('`');
			condition.append(((FieldDefinition) b).getName());
			condition.append('`');
		} else if (b instanceof String) {
			condition.append('\'');
			condition.append(b);
			condition.append('\'');
		} else {
			condition.append(b);
		}

		return this;
	}

	@Override
	public SQLConditionBuilder and() {
		condition.append(" AND ");

		return this;
	}

	@Override
	public SQLConditionBuilder or() {
		condition.append(" OR ");

		return this;
	}

	@Override
	public ConditionBuilder parse(String source) {
		condition.append(RestQL.parseToSQL(source));

		return this;
	}

	@Override
	public String build() {
		return condition.toString();
	}

}