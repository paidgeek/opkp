package si.opkp.util;

import si.opkp.batch.*;

public class Validator {

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
