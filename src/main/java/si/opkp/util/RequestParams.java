package si.opkp.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.moybl.restql.RestQL;
import com.moybl.restql.Token;
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
	private AstNode skip;
	private AstNode take;

	public RequestParams() {
		skip = new Literal(0, Token.NUMBER);
		take = new Literal(50, Token.NUMBER);
		fields = new Sequence(Collections.emptyList());
		sort = new Sequence(Collections.emptyList());
		group = new Sequence(Collections.emptyList());
	}

	@JsonCreator
	public RequestParams(@JsonProperty("fields") List<String> fieldList,
								@JsonProperty("sort") List<String> sortList,
								@JsonProperty("group") List<String> groupList,
								@JsonProperty("where") String where,
								@JsonProperty("skip") Integer skip,
								@JsonProperty("take") Integer take) {
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
			this.skip = new Literal(skip, Token.NUMBER);
		}

		if (take != null) {
			this.take = new Literal(take, Token.NUMBER);
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
		this.skip = RestQL.parse(skip);
	}

	public void setTake(String take) {
		this.take = RestQL.parse(take);
	}

	public AstNode getSkip() {
		return skip;
	}

	public AstNode getTake() {
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