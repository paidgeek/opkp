package si.opkp.model;

public class FieldDefinition {

	private String name;
	private String model;
	private FieldType type;
	private boolean notNull;
	private boolean identifier;
	private Object defaultValue;

	public FieldDefinition(String name, String model, FieldType type, boolean notNull, boolean identifier, Object defaultValue) {
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

	public FieldType getType() {
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
