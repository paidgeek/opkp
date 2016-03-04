package si.opkp.util;

import si.opkp.batch.Batch;
import si.opkp.batch.Command;

import java.util.ArrayList;

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

			if (command.hasParam("limit") && ((ArrayList) command.getParam("limit")).isEmpty()) {
				return "empty limit array";
			}

			if (command.hasParam("columns") && ((ArrayList) command.getParam("columns")).isEmpty()) {
				return "no columns selected";
			}
		}

		return null;
	}

}
