package si.opkp.model;

import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import si.opkp.batch.*;
import si.opkp.util.Pojo;
import si.opkp.query.RequestColumn;

public class Validator {

	public static Optional<String> validatePartial(String table, Pojo body) {
		TableDefinition td = DataDefinition.getInstance()
													  .getDefinition(table);

		if (td == null) {
			return Optional.of("table does not exist");
		}

		Set<String> intersection = Sets.difference(body.getProperties()
																	  .keySet(), td.getFields()
																						.keySet());

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
		TableDefinition td = DataDefinition.getInstance()
													  .getDefinition(table);

		if (td == null) {
			return Optional.of("table does not exist");
		}

		for (Map.Entry<String, FieldDefinition> cd : td.getFields()
																	  .entrySet()) {
			FieldDefinition fd = cd.getValue();

			if (fd.isNotNull() && fd.getDefaultValue() == null && body.getProperty(cd.getKey()) == null) {
				return Optional.of("column '" + cd.getKey() + "' cannot be null");
			}
		}

		Set<String> intersection = Sets.difference(body.getProperties()
																	  .keySet(), td.getFields()
																						.keySet());

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

	public static Optional<String> validate(String model, List<String> columns) {
		TableDefinition td = DataDefinition.getInstance()
													  .getDefinition(model);

		if (td == null) {
			return Optional.of("unknown model '" + model + "'");
		}

		columns.removeAll(td.getFields()
								  .keySet());

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

	public static Optional<String> validate(String[] path, RequestColumn[] columns, RequestColumn[] sort) {
		List<String> cols = new ArrayList<>(columns.length);

		for (int i = 0; i < columns.length; i++) {
			cols.add(columns[i].getName());
		}

		if (!cols.isEmpty() && cols.get(0)
											.equals("*")) {
			return Optional.empty();
		}

		for (String node : path) {
			TableDefinition td = DataDefinition.getInstance()
														  .getDefinition(node);

			if (td == null) {
				return Optional.of("unknown model '" + node + "'");
			}

			cols.removeAll(td.getFields()
								  .keySet());
		}

		if (!cols.isEmpty()) {
			StringBuilder err = new StringBuilder();

			err.append("unknown columns: ");

			for (int i = 0; i < cols.size(); i++) {
				err.append('\'');
				err.append(cols.get(i));
				err.append('\'');

				if (i < cols.size() - 1) {
					err.append(", ");
				}
			}

			return Optional.of(err.toString());
		}

		return Optional.empty();
	}

	public static Optional<String> validate(Batch batch) {
		if (batch.getCommands() == null || batch.getCommands()
															 .isEmpty()) {
			return Optional.of("no commands in batch");
		}

		for (Command command : batch.getCommands()) {
			if (command.getName()
						  .isEmpty()) {
				return Optional.of("command must have a name");
			}

			if (command.getController()
						  .isEmpty()) {
				return Optional.of("command must have a controller specified");
			}

			if (command.getModel()
						  .isEmpty()) {
				return Optional.of("command must have a model specified");
			}
		}

		return Optional.empty();
	}

}
