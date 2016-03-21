package si.opkp.batch;

import com.fasterxml.jackson.annotation.*;

import org.springframework.http.HttpMethod;

import si.opkp.util.*;

import java.util.*;

public class Command {

	private String name;
	private HttpMethod method;
	private String controller;
	private String model;
	private Pojo body;
	private RestDto params;
	private List<String> dependencies;

	@JsonCreator
	public Command(@JsonProperty("name") String name,
						@JsonProperty("method") HttpMethod method,
						@JsonProperty("controller") String controller,
						@JsonProperty("model") String model,
						@JsonProperty("body") Pojo body,
						@JsonProperty("params") RestDto params,
						@JsonProperty("dependencies") List<String> dependencies) {
		this.name = name == null ? "" : name;
		this.method = method == null ? HttpMethod.GET : method;
		this.controller = controller == null ? "" : controller;
		this.model = model == null ? "" : model;
		this.body = body == null ? new Pojo() : body;
		this.dependencies = dependencies == null ? new ArrayList<>() : dependencies;
		this.params = params;
	}

	public String getName() {
		return name;
	}

	public HttpMethod getMethod() {
		return method;
	}

	public String getController() {
		return controller;
	}

	public String getModel() {
		return model;
	}

	public Pojo getBody() {
		return body;
	}

	public RestDto getParams() {
		return params;
	}

	public List<String> getDependencies() {
		return dependencies;
	}

}
