package si.opkp.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@JsonDeserialize(using = RequestParamsDeserializer.class)
public class RequestParams {

	private RequestColumn[] columns;
	private RequestColumn[] sort;
	private String query;
	private Integer[] limit;
	private String[] keywords;
	private String language;

	public RequestParams() {
		columns = new RequestColumn[]{new RequestColumn("*")};
		limit = new Integer[]{0, 100};
		keywords = new String[]{};
		language = "en";
	}

	@JsonCreator
	public RequestParams(@JsonProperty("columns") List<String> columnList,
								@JsonProperty("sort") List<String> sortList,
								@JsonProperty("q") String query,
								@JsonProperty("body") List<Integer> limitList,
								@JsonProperty("keywords") List<String> keywordList,
								@JsonProperty("language") String language) {
		this();

		this.query = query;

		if (columnList != null) {
			columns = new RequestColumn[columnList.size()];

			for (int i = 0; i < columns.length; i++) {
				columns[i] = new RequestColumn(columnList.get(i));
			}
		}

		if (sortList != null) {
			sort = new RequestColumn[sortList.size()];

			for (int i = 0; i < sort.length; i++) {
				sort[i] = new RequestColumn(sortList.get(i));
			}
		}

		if (keywordList != null) {
			keywords = new String[keywordList.size()];
			keywordList.toArray(keywords);
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
		String[] cols = columns.split(",");
		this.columns = new RequestColumn[cols.length];

		for (int i = 0; i < cols.length; i++) {
			this.columns[i] = new RequestColumn(cols[i]);
		}
	}

	public void setSort(String sort) {
		String[] cols = sort.split(",");
		this.sort = new RequestColumn[cols.length];

		for (int i = 0; i < cols.length; i++) {
			this.sort[i] = new RequestColumn(cols[i]);
		}
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

	public RequestColumn[] getColumns() {
		return columns;
	}

	public String[] getKeywords() {
		return keywords;
	}

	public RequestColumn[] getSort() {
		return sort;
	}

	public String getLanguage() {
		return language;
	}

}
