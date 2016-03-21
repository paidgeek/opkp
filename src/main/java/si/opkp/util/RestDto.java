package si.opkp.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.moybl.restql.RestQL;

import java.util.ArrayList;
import java.util.List;

@JsonDeserialize(using = RestDtoDeserializer.class)
public class RestDto {

	private List<String> columns;
	private List<String> sort;
	private String query;
	private List<Integer> limit;
	private List<String> keywords;
	private String language;

	public RestDto() {
		columns = Util.stringList("*");
		limit = Util.integerList(0, 100);
		keywords = new ArrayList<>();
		language = "en";
	}

	@JsonCreator
	public RestDto(@JsonProperty("columns") List<String> columns,
						@JsonProperty("sort") List<String> sort,
						@JsonProperty("q") String query,
						@JsonProperty("body") List<Integer> limit,
						@JsonProperty("keywords") List<String> keywords,
						@JsonProperty("language") String language) {
		this.columns = columns == null ? Util.stringList("*") : columns;
		this.sort = sort;
		this.query = RestQL.parseToSQL(query);
		this.limit = limit == null ? Util.integerList(0, 100) : limit;
		this.keywords = keywords;
		this.language = language == null ? "en" : language;
	}

	public void setColumns(String columns) {
		this.columns = Util.parseStringList(columns);
	}

	public void setSort(String sort) {
		this.sort = Util.parseStringList(sort);
	}

	public void setQuery(String query) {
		this.query = RestQL.parseToSQL(query);
	}

	public void setLimit(String limit) {
		this.limit = Util.parseIntegerList(limit);
	}

	public void setKeywords(String keywords) {
		this.keywords = Util.parseStringList(keywords);
	}

	public void setLanguage(String language) {
		this.language = language;
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

	public List<Integer> getLimit() {
		return limit;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public String getLanguage() {
		return language;
	}

}
