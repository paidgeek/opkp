package si.opkp.model;

public class FieldDefinition {

	private String name;
	private String node;
	private FieldType type;
	private boolean notNull;
	private boolean identifier;
	private Object defaultValue;

	public FieldDefinition(String name, String node, FieldType type, boolean notNull, boolean identifier, Object defaultValue) {
		this.name = name;
		this.node = node;
		this.type = type;
		this.notNull = notNull;
		this.identifier = identifier;
		this.defaultValue = defaultValue;
	}

	public String getName() {
		return name;
	}

	public String getNode() {
		return node;
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
