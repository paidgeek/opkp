package si.opkp.model;

public class FieldDefinition {

	public enum Type {
		STRING,
		DATETIME,
		DECIMAL,
		INTEGER
	}

	private String name;
	private String model;
	private Type type;
	private boolean notNull;
	private boolean identifier;
	private Object defaultValue;

	public FieldDefinition(String name, String model, Type type, boolean notNull, boolean identifier, Object defaultValue) {
		this.name = name;
		this.model = model;
		this.type = type;
		this.notNull = notNull;
		this.identifier = identifier;
		this.defaultValue = defaultValue;
	}

	public String getName() {
		return name;
	}

	public String getModel() {
		return model;
	}

	public Type getType() {
		return type;
	}

	public boolean isNotNull() {
		return notNull;
	}

	public boolean isIdentifier() {
		return identifier;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

}
