package si.opkp.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.moybl.restql.RestQL;
import com.moybl.restql.ast.AstNode;
import com.moybl.restql.ast.Literal;
import com.moybl.restql.ast.Sequence;

import java.util.Collections;
import java.util.List;

@JsonDeserialize(using = RequestParamsDeserializer.class)
public class RequestParams {

	private Sequence fields;
	private Sequence sort;
	private Sequence group;
	private AstNode where;
	private long skip;
	private long take;

	public RequestParams() {
		skip = 0;
		take = 50;
		fields = new Sequence(Collections.emptyList());
		sort = new Sequence(Collections.emptyList());
		group = new Sequence(Collections.emptyList());
	}

	@JsonCreator
	public RequestParams(@JsonProperty("fields") List<String> fieldList,
								@JsonProperty("sort") List<String> sortList,
								@JsonProperty("group") List<String> groupList,
								@JsonProperty("where") String where,
								@JsonProperty("skip") Long skip,
								@JsonProperty("take") Long take) {
		this();

		if (fieldList != null) {
			setFields(String.join(",", fieldList));
		}

		if (sortList != null) {
			setSort(String.join(",", sortList));
		}

		if (groupList != null) {
			setGroup(String.join(",", groupList));
		}

		if (where != null) {
			setWhere(where);
		}

		if (skip != null) {
			this.skip = skip;
		}

		if (take != null) {
			this.take = take;
		}
	}

	public void setFields(String fieldList) {
		AstNode node = RestQL.parse(fieldList)
									.getElements()
									.get(0);

		if (node instanceof Sequence) {
			fields = (Sequence) node;
		} else {
			fields = new Sequence(Collections.singletonList(node));
		}
	}

	public void setSort(String sortList) {
		AstNode node = RestQL.parse(sortList)
									.getElements()
									.get(0);

		if (node instanceof Sequence) {
			sort = (Sequence) node;
		} else {
			sort = new Sequence(Collections.singletonList(node));
		}
	}

	public void setGroup(String groupList) {
		AstNode node = RestQL.parse(groupList)
									.getElements()
									.get(0);

		if (node instanceof Sequence) {
			group = (Sequence) node;
		} else {
			group = new Sequence(Collections.singletonList(node));
		}
	}

	public void setWhere(String where) {
		this.where = RestQL.parse(where);
	}

	public void setSkip(String skip) {
		// TODO there has to be a better way
		this.skip = (long) ((Literal) ((Sequence) RestQL.parse(skip)
																		.getElements()
																		.get(0)).getElements()
																				  .get(0))
				.numberValue();
	}

	public void setTake(String take) {
		this.take = (long) ((Literal) ((Sequence) RestQL.parse(take)
																		.getElements()
																		.get(0)).getElements()
																				  .get(0))
				.numberValue();
	}

	public long getSkip() {
		return skip;
	}

	public long getTake() {
		return take;
	}

	public AstNode getWhere() {
		return where;
	}

	public Sequence getFields() {
		return fields;
	}

	public Sequence getGroup() {
		return group;
	}

	public Sequence getSort() {
		return sort;
	}

}