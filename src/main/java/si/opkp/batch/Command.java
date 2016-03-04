package si.opkp.batch;

import java.util.List;
import java.util.Map;

public class Command {

	private String name;
	private String controller;
	private String model;
	private Map<String, Object> params;
	private List<String> dependencies;

	public String getName() {
		return name;
	}

	public String getController() {
		return controller;
	}

	public String getModel() {
		return model;
	}

	public boolean hasParam(String name) {
		return params.containsKey(name);
	}

	public Object getParam(String name) {
		return params.get(name);
	}

	public Map<String, Object> getParams() {
		return params;
	}

}
