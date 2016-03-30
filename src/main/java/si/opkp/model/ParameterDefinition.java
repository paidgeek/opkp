package si.opkp.model;

public class ParameterDefinition {

	private String name;
	private FieldType type;

	public ParameterDefinition(String name, FieldType type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public FieldType getType() {
		return type;
	}

}
