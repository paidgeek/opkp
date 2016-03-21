package si.opkp.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.moybl.restql.RestQL;

import java.util.ArrayList;
import java.util.List;

@JsonDeserialize(using = RestDtoDeserializer.class)
public class RestDto {

	private String[] columns;
	private String[] sort;
	private String query;
	private Integer[] limit;
	private String[] keywords;
	private String language;

	public RestDto() {
		columns = new String[]{"*"};
		limit = new Integer[]{0, 100};
		keywords = new String[]{};
		language = "en";
	}

	@JsonCreator
	public RestDto(@JsonProperty("columns") List<String> columnList,
						@JsonProperty("sort") List<String> sortList,
						@JsonProperty("q") String query,
						@JsonProperty("body") List<Integer> limitList,
						@JsonProperty("keywords") List<String> keywordList,
						@JsonProperty("language") String language) {
		this();

		this.query = query;

		if (columnList != null) {
			columns = new String[columnList.size()];
			columnList.toArray(columns);
		}

		if (sortList != null) {
			sort = new String[sortList.size()];
			sortList.toArray(sort);
		}

		if (keywordList != null) {
			keywords = new String[keywordList.size()];
			keywordList.toArray(keywords);
		}

		if (sortList != null) {
			sort = new String[sortList.size()];
			sortList.toArray(sort);
		}

		if (language != null && !language.isEmpty()) {
			this.language = language;
		}

		if (limitList != null && !limitList.isEmpty()) {
			if (limitList.size() == 1) {
				limit[1] = limitList.get(0);
			} else {
				limit[0] = limitList.get(0);
				limit[1] = limitList.get(1);
			}
		}
	}

	public void setColumns(String columns) {
		this.columns = columns.split(",");
	}

	public void setSort(String sort) {
		this.sort = sort.split(",");
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public void setLimit(String limit) {
		String[] bounds = limit.split(",");

		if (bounds.length == 1) {
			this.limit[1] = Integer.parseInt(bounds[0]);
		} else if (bounds.length == 2) {
			this.limit[0] = Integer.parseInt(bounds[0]);
			this.limit[1] = Integer.parseInt(bounds[1]);
		}
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords.split(",");
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Integer[] getLimit() {
		return limit;
	}

	public String getQuery() {
		return query;
	}

	public String[] getColumns() {
		return columns;
	}

	public String[] getKeywords() {
		return keywords;
	}

	public String[] getSort() {
		return sort;
	}

	public String getLanguage() {
		return language;
	}

}
