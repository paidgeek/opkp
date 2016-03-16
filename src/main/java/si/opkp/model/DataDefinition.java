package si.opkp.model;

import si.opkp.util.*;

import java.util.*;

public class DataDefinition {

	private static DataDefinition instance;

	public static DataDefinition getInstance() {
		if (instance == null) {
			synchronized (DataDefinition.class) {
				if (instance == null) {
					instance = new DataDefinition();

					return instance;
				}
			}
		}

		return instance;
	}

	static {
		getInstance();
	}

	private Map<String, Map<String, FieldDefinition>> definitions;

	private DataDefinition() {
		HashMap<String, Object> dd = (HashMap) Util.readFile("classpath:data-definition.json");
		definitions = new HashMap<>();

		// TODO "maybe" contains bugs
		for (Map.Entry<String, Object> table : dd.entrySet()) {
			HashMap<String, Map> cols = (HashMap) table.getValue();
			Map<String, FieldDefinition> defs = new HashMap<>();

			for (Map.Entry<String, Map> col : cols.entrySet()) {
				Map<String, Object> info = col.getValue();
				String typeString = (String) info.get("type");
				FieldDefinition.Type type = null;

				if (typeString.startsWith("varchar") || typeString.equalsIgnoreCase("text")) {
					type = FieldDefinition.Type.STRING;
				} else if (typeString.startsWith("int") || typeString.startsWith("tinyint")) {
					type = FieldDefinition.Type.INTEGER;
				} else if (typeString.startsWith("datetime")) {
					type = FieldDefinition.Type.DATETIME;
				} else if (typeString.startsWith("decimal")) {
					type = FieldDefinition.Type.DECIMAL;
				}

				boolean notNull = (Boolean) info.get("notNull");
				boolean key = !((String) info.get("key")).isEmpty();
				Object defaultValue = info.get("defaultValue");
				String extra = (String) info.get("extra");

				defs.put(col.getKey(), new FieldDefinition(type, notNull, key, defaultValue, extra));
			}

			definitions.put(table.getKey(), defs);
		}
	}

	public FieldDefinition getDefinition(String table, String column) {
		if (definitions.containsKey(table)) {
			return definitions.get(table).get(column);
		}

		return null;
	}

	public Map<String, FieldDefinition> getDefinitions(String table) {
		if (definitions.containsKey(table)) {
			return definitions.get(table);
		}

		return null;
	}

}
