package si.opkp.batch;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.moybl.restql.ast.AstNode;

import org.springframework.http.HttpMethod;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import si.opkp.util.Pojo;
import si.opkp.util.RequestParams;
import si.opkp.util.Util;

public class Command {

	private String name;
	private HttpMethod method;
	private String controller;
	private List<String> path;
	private Pojo body;
	private RequestParams params;
	private List<Dependency> dependencies;

	@JsonCreator
	public Command(@JsonProperty("name") String name,
						@JsonProperty("method") HttpMethod method,
						@JsonProperty("controller") String controller,
						@JsonProperty("path") String path,
						@JsonProperty("body") Pojo body,
						@JsonProperty("params") RequestParams params,
						@JsonProperty("dependencies") List<Dependency> dependencies) {
		this.name = name;
		this.method = method == null ? HttpMethod.GET : method;
		this.controller = controller;
		this.path = Arrays.asList(path.split("/"));
		this.body = body;
		this.dependencies = dependencies == null ? Collections.emptyList() : dependencies;
		this.params = params == null ? new RequestParams() : params;
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

	public List<String> getPath() {
		return path;
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