package si.opkp.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import si.opkp.util.*;

import java.util.*;

import javax.annotation.PostConstruct;

@Component
public class DataDefinition {

	private static DataDefinition instance;

	public static DataDefinition getInstance() {
		return instance;
	}

	@Autowired
	private Database db;
	private Map<String, TableDefinition> definitions;

	@PostConstruct
	private void init() {
		definitions = new HashMap<>();
		List<Pojo> tables = db.queryObjects("SHOW TABLES");

		for (Pojo tableResult : tables) {
			Map<String, FieldDefinition> defs = new HashMap<>();
			Set<FieldDefinition> primaryKeys = new HashSet<>();

			String tableName = tableResult.getProperties()
													.values()
													.iterator()
													.next()
													.toString();
			List<Pojo> columns = db.queryObjects("SHOW COLUMNS FROM `" + tableName + "`");

			for (Pojo col : columns) {
				String typeString = col.getString("Type");
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

				boolean notNull = col.getString("Null")
											.equals("NO");
				String key = col.getString("Key");
				Object defaultValue = col.getProperty("Default");
				String extra = col.getString("Extra");

				FieldDefinition fd = new FieldDefinition(col.getString("Field"), type, notNull, !key.isEmpty(), defaultValue, extra);

				if (key.equals("PRI")) {
					primaryKeys.add(fd);
				}

				defs.put(col.getString("Field"), fd);
			}

			definitions.put(tableName, new TableDefinition(tableName, defs, primaryKeys));
		}

		instance = this;
	}

	public TableDefinition getDefinition(String table) {
		return definitions.get(table);
	}

	public Set<String> getTables() {
		return definitions.keySet();
	}

}
