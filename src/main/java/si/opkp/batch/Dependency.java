package si.opkp.batch;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Dependency {

	private String command;
	private String condition;

	@JsonCreator
	public Dependency(@JsonProperty("command") String command,
							@JsonProperty("condition") String condition) {
		this.command = command;
		this.condition = condition;
	}

	public String getCommand() {
		return command;
	}

	public String getCondition() {
		return condition;
	}

}
