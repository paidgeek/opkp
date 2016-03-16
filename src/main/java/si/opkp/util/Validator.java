package si.opkp.util;

import java.lang.reflect.Field;
import java.util.Map;

import si.opkp.batch.*;
import si.opkp.model.DataDefinition;
import si.opkp.model.FieldDefinition;

public class Validator {

	public static String validate(String table, Pojo body) {
		Map<String, FieldDefinition> td = DataDefinition.getInstance().getDefinitions(table);

		if (td == null) {
			return "table does not exist";
		}

		for (Map.Entry<String, Object> prop : body.getProperties().entrySet()) {
			FieldDefinition fd = td.get(prop.getKey());

			if (fd == null) {
				return "unknown column '" + prop.getKey() + "'";
			}

			if (fd.isNotNull() && fd.getDefaultValue() == null && prop.getValue() == null) {
				return "column '" + prop.getKey() + "' cannot be null";
			}
		}

		return null;
	}

	public static String validate(Batch batch) {
		if (batch.getCommands().isEmpty()) {
			return "no commands in batch";
		}

		for (Command command : batch.getCommands()) {
			if (command.getName().isEmpty()) {
				return "command must have a name";
			}

			if (command.getController().isEmpty()) {
				return "command must have a controller specified";
			}

			if (command.getModel().isEmpty()) {
				return "command must have a model specified";
			}

			if (command.getLimit() != null && command.getLimit().isEmpty()) {
				return "empty limit array";
			}

			if (command.getColumns() != null && command.getColumns().isEmpty()) {
				return "no columns selected";
			}
		}

		return null;
	}

}
