package si.opkp.util;

import si.opkp.model.FieldDefinition;

public class SQLConditionBuilder {

	private StringBuilder condition;

	public SQLConditionBuilder() {
		condition = new StringBuilder();
	}

	public SQLConditionBuilder equal(Object a, Object b) {
		if (a instanceof FieldDefinition) {
			condition.append('`');
			condition.append(((FieldDefinition) a).getName());
			condition.append('`');
		} else if (a instanceof String) {
			condition.append('\'');
			condition.append(a);
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

	public SQLConditionBuilder and() {
		condition.append(" AND ");

		return this;
	}

	public SQLConditionBuilder or() {
		condition.append(" OR ");

		return this;
	}

	public String build() {
		return condition.toString();
	}

}
