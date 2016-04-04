package si.opkp.model;

import java.util.Map;
import java.util.Set;

public class NodeDefinition {

	private String name;
	private Set<FieldDefinition> identifiers;
	private Map<String, FieldDefinition> fields;

	public NodeDefinition(String name, Map<String, FieldDefinition> fields, Set<FieldDefinition> identifiers) {
		this.name = name;
		this.identifiers = identifiers;
		this.fields = fields;
	}

	public String getName() {
		return name;
	}

	public Map<String, FieldDefinition> getFields() {
		return fields;
	}

	public FieldDefinition getField(String name) {
		return fields.get(name);
	}

	public Set<FieldDefinition> getIdentifiers() {
		return identifiers;
	}

}
