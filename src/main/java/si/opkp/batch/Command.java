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
	private String query;
	private List<String> columns;
	private List<Integer> limit;
	private List<String> sort;
	private List<String> keywords;
	private String language;
	private List<String> dependencies;

	@JsonCreator
	public Command(@JsonProperty("name") String name,
						@JsonProperty("method") HttpMethod method,
						@JsonProperty("controller") String controller,
						@JsonProperty("model") String model,
						@JsonProperty("body") Pojo body,
						@JsonProperty("params") HashMap<String, Object> params,
						@JsonProperty("dependencies") List<String> dependencies) {
		this.name = name == null ? "" : name;
		this.method = method == null ? HttpMethod.GET : method;
		this.controller = controller == null ? "" : controller;
		this.model = model == null ? "" : model;
		this.body = body == null ? new Pojo() : body;
		this.dependencies = dependencies == null ? new ArrayList<>() : dependencies;

		query = (String) params.get("q");
		columns = (ArrayList) params.get("columns");
		keywords = (ArrayList) params.get("keywords");
		limit = (ArrayList) params.get("limit");
		language = (String) params.get("lang");
		sort = (ArrayList) params.get("sort");
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

	public List<Integer> getLimit() {
		return limit;
	}

	public List<String> getColumns() {
		return columns;
	}

	public List<String> getSort() {
		return sort;
	}

	public String getQuery() {
		return query;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public String getLanguage() {
		return language;
	}

	public List<String> getDependencies() {
		return dependencies;
	}

}
