package si.opkp.model;

public class FieldDefinition {

	public enum Type {
		STRING,
		DATETIME,
		DECIMAL,
		INTEGER
	}

	private String name;
	private String tableName;
	private Type type;
	private boolean notNull;
	private boolean key;
	private Object defaultValue;
	private String extra;

	public FieldDefinition(String name, String tableName, Type type, boolean notNull, boolean key, Object defaultValue, String extra) {
		this.name = name;
		this.tableName = tableName;
		this.type = type;
		this.notNull = notNull;
		this.key = key;
		this.defaultValue = defaultValue;
		this.extra = extra;
	}

	public String getName() {
		return name;
	}

	public String getTableName() {
		return tableName;
	}

	public Type getType() {
		return type;
	}

	public boolean isNotNull() {
		return notNull;
	}

	public boolean isKey() {
		return key;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	public String getExtra() {
		return extra;
	}

}
