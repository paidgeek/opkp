package si.opkp.model;

public class FieldDefinition {

	public enum Type {
		STRING,
		DATETIME,
		DECIMAL,
		INTEGER
	}

	private Type type;
	private boolean notNull;
	private boolean key;
	private Object defaultValue;
	private String extra;

	public FieldDefinition(Type type, boolean notNull, boolean key, Object defaultValue, String extra) {
		this.type = type;
		this.notNull = notNull;
		this.key = key;
		this.defaultValue = defaultValue;
		this.extra = extra;
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
