package si.opkp.model;

import java.util.Map;
import java.util.Set;

public class TableDefinition {

	private String name;
	private Set<FieldDefinition> primaryKeys;
	private Map<String, FieldDefinition> fields;

	public TableDefinition(String name, Map<String, FieldDefinition> fields, Set<FieldDefinition> primaryKeys) {
		this.name = name;
		this.primaryKeys = primaryKeys;
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

	public Set<FieldDefinition> getPrimaryKeys() {
		return primaryKeys;
	}

}
