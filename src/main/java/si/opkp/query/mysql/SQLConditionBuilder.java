package si.opkp.query.mysql;

import com.moybl.restql.RestQL;

import si.opkp.model.FieldDefinition;
import si.opkp.query.ConditionBuilder;
import si.opkp.query.Field;

public class SQLConditionBuilder implements ConditionBuilder {

	private StringBuilder condition;

	public SQLConditionBuilder() {
		condition = new StringBuilder();
	}

	@Override
	public SQLConditionBuilder equal(Object a, Object b) {
		if (a instanceof FieldDefinition) {
			FieldDefinition fa = (FieldDefinition) a;

			condition.append(fa.getModel());
			condition.append(".");
			condition.append(fa.getName());
		} else if (a instanceof Field) {
			condition.append('\'');
			condition.append(((Field) a).getName());
			condition.append('\'');
		} else {
			condition.append(a);
		}

		condition.append(" = ");

		if (b instanceof FieldDefinition) {
			FieldDefinition fb = (FieldDefinition) b;

			condition.append(fb.getModel());
			condition.append(".");
			condition.append(fb.getName());
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