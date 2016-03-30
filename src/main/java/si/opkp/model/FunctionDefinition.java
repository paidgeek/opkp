package si.opkp.model;

import java.util.List;

public class FunctionDefinition {

	private String name;
	private List<ParameterDefinition> parameters;

	public FunctionDefinition(String name, List<ParameterDefinition> parameters) {
		this.name = name;
		this.parameters = parameters;
	}

	public String getName() {
		return name;
	}

	public List<ParameterDefinition> getParameters() {
		return parameters;
	}

}
