package si.opkp.model;

import com.google.common.collect.Sets;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import si.opkp.batch.*;
import si.opkp.util.Pojo;

public class Validator {

	public static Optional<String> validatePartial(String table, Pojo body) {
		Map<String, FieldDefinition> td = DataDefinition.getInstance().getDefinitions(table);

		if (td == null) {
			return Optional.of("table does not exist");
		}

		Set<String> intersection = Sets.difference(body.getProperties().keySet(), td.keySet());

		if (!intersection.isEmpty()) {
			StringBuilder err = new StringBuilder();
			Iterator<String> i = intersection.iterator();

			err.append("unknown columns: ");

			while (i.hasNext()) {
				err.append('\'');
				err.append(i.next());
				err.append('\'');

				if (i.hasNext()) {
					err.append(", ");
				}
			}

			return Optional.of(err.toString());
		}

		return Optional.empty();
	}

	public static Optional<String> validateFull(String table, Pojo body) {
		Map<String, FieldDefinition> td = DataDefinition.getInstance().getDefinitions(table);

		if (td == null) {
			return Optional.of("table does not exist");
		}

		for (Map.Entry<String, FieldDefinition> cd : td.entrySet()) {
			FieldDefinition fd = cd.getValue();

			if (fd.isNotNull() && fd.getDefaultValue() == null && body.getProperty(cd.getKey()) == null) {
				return Optional.of("column '" + cd.getKey() + "' cannot be null");
			}
		}

		Set<String> intersection = Sets.difference(body.getProperties().keySet(), td.keySet());

		if (!intersection.isEmpty()) {
			StringBuilder err = new StringBuilder();
			Iterator<String> i = intersection.iterator();

			err.append("unknown columns: ");

			while (i.hasNext()) {
				err.append('\'');
				err.append(i.next());
				err.append('\'');

				if (i.hasNext()) {
					err.append(", ");
				}
			}

			return Optional.of(err.toString());
		}

		return Optional.empty();
	}

	public static Optional<String> validate(List<String> path, List<String> columns, List<String> sort) {
		for (String node : path) {
			Map<String, FieldDefinition> td = DataDefinition.getInstance().getDefinitions(node);

			if (td == null) {
				return Optional.of("unknown model '" + node + "'");
			}

			columns.removeAll(td.keySet());
		}

		if (!columns.isEmpty()) {
			StringBuilder err = new StringBuilder();

			err.append("unknown columns: ");

			for (int i = 0; i < columns.size(); i++) {
				err.append('\'');
				err.append(columns.get(i));
				err.append('\'');

				if (i < columns.size() - 1) {
					err.append(", ");
				}
			}

			return Optional.of(err.toString());
		}

		return Optional.empty();
	}

	public static Optional<String> validate(Batch batch) {
		if (batch.getCommands().isEmpty()) {
			return Optional.of("no commands in batch");
		}

		for (Command command : batch.getCommands()) {
			if (command.getName().isEmpty()) {
				return Optional.of("command must have a name");
			}

			if (command.getController().isEmpty()) {
				return Optional.of("command must have a controller specified");
			}

			if (command.getModel().isEmpty()) {
				return Optional.of("command must have a model specified");
			}

			if (command.getLimit() != null && command.getLimit().isEmpty()) {
				return Optional.of("empty limit array");
			}

			if (command.getColumns() != null && command.getColumns().isEmpty()) {
				return Optional.of("no columns selected");
			}
		}

		return Optional.empty();
	}

}
