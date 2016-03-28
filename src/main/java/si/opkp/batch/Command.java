package si.opkp.batch;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.moybl.restql.ast.Identifier;

import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;

import si.opkp.util.Pojo;
import si.opkp.util.RequestParams;
import si.opkp.util.Util;

public class Command {

	private String name;
	private HttpMethod method;
	private String controller;
	private List<Identifier> arguments;
	private Pojo body;
	private RequestParams params;
	private List<Dependency> dependencies;

	@JsonCreator
	public Command(@JsonProperty("name") String name,
						@JsonProperty("method") HttpMethod method,
						@JsonProperty("controller") String controller,
						@JsonProperty("arguments") String arguments,
						@JsonProperty("body") Pojo body,
						@JsonProperty("params") RequestParams params,
						@JsonProperty("dependencies") List<Dependency> dependencies) {
		this.name = name;
		this.method = method == null ? HttpMethod.GET : method;
		this.controller = controller;
		this.arguments = Util.parseQueryArguments(arguments);
		this.body = body;
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

	public List<Identifier> getArguments() {
		return arguments;
	}

	public Pojo getBody() {
		return body;
	}

	public RequestParams getParams() {
		return params;
	}

	public List<Dependency> getDependencies() {
		return dependencies;
	}

}