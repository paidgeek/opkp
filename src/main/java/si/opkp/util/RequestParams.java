package si.opkp.util;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import si.opkp.query.Field;

@JsonDeserialize(using = RequestParamsDeserializer.class)
public class RequestParams {

	private Field[] fields;
	private Field[] sort;
	private Field[] group;
	private String query;
	private int skip;
	private int take;

	public RequestParams() {
		skip = 0;
		take = 100;
	}

	public Field[] getFields() {
		return fields;
	}

	public void setFields(Field[] fields) {
		this.fields = fields;
	}

	public Field[] getSort() {
		return sort;
	}

	public void setSort(Field[] sort) {
		this.sort = sort;
	}

	public Field[] getGroup() {
		return group;
	}

	public void setGroup(Field[] group) {
		this.group = group;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public int getSkip() {
		return skip;
	}

	public void setSkip(int skip) {
		this.skip = skip;
	}

	public int getTake() {
		return take;
	}

	public void setTake(int take) {
		this.take = take;
	}

	// setters for @ModelAttribute
	public void setFields(String fieldList) {
		String[] cols = fieldList.split(",");
		this.fields = new Field[cols.length];

		for (int i = 0; i < cols.length; i++) {
			this.fields[i] = new Field(cols[i]);
		}
	}

	public void setSort(String sortList) {
		String[] cols = sortList.split(",");
		this.sort = new Field[cols.length];

		for (int i = 0; i < cols.length; i++) {
			this.sort[i] = new Field(cols[i]);
		}
	}

	public void setGroup(String groupList) {
		String[] cols = groupList.split(",");
		this.group = new Field[cols.length];

		for (int i = 0; i < cols.length; i++) {
			this.group[i] = new Field(cols[i]);
		}
	}

}
