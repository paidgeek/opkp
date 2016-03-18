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

	private Map<String, TableDefinition> definitions;

	private DataDefinition() {
		HashMap<String, Object> dd = (HashMap) Util.readJSONFile("classpath:data-definition.json");
		definitions = new HashMap<>();

		// TODO "maybe" contains bugs
		for (Map.Entry<String, Object> table : dd.entrySet()) {
			HashMap<String, Map> cols = (HashMap) table.getValue();
			Map<String, FieldDefinition> defs = new HashMap<>();
			Set<FieldDefinition> primaryKeys = new HashSet<>();

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
				String key = (String) info.get("key");
				Object defaultValue = info.get("defaultValue");
				String extra = (String) info.get("extra");

				FieldDefinition fd = new FieldDefinition(col.getKey(), type, notNull, !key.isEmpty(), defaultValue, extra);

				if (key.equals("PRI")) {
					primaryKeys.add(fd);
				}

				defs.put(col.getKey(), fd);
			}

			definitions.put(table.getKey(), new TableDefinition(table.getKey(), defs, primaryKeys));
		}
	}

	public TableDefinition getDefinition(String table) {
		return definitions.get(table);
	}

	public Set<String> getTables() {
		return definitions.keySet();
	}

}
