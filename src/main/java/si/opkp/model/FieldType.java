package si.opkp.model;

import java.sql.Types;

public enum FieldType {
	STRING,
	DATETIME,
	DECIMAL,
	INTEGER;

	public static FieldType fromSQLType(int type) {
		switch (type) {
			case Types.INTEGER:
			case Types.TINYINT:
			case Types.BIT:
			case Types.BIGINT:
				return FieldType.INTEGER;
			case Types.DATE:
			case Types.TIMESTAMP:
			case Types.TIME:
				return FieldType.DATETIME;
			case Types.NVARCHAR:
			case Types.VARCHAR:
			case Types.LONGNVARCHAR:
			case Types.LONGVARCHAR:
			case Types.LONGVARBINARY:
			case Types.CHAR:
			case Types.NCHAR:
				return FieldType.STRING;
			case Types.DECIMAL:
			case Types.FLOAT:
			case Types.DOUBLE:
				return FieldType.DECIMAL;
		}

		throw new RuntimeException("field type not defined");
	}

}
