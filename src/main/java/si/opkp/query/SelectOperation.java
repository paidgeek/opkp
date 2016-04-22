package si.opkp.query;

import com.moybl.restql.ast.AstNode;

import java.util.ArrayList;
import java.util.List;

import si.opkp.util.Graph;
import si.opkp.util.RequestField;
import si.opkp.util.RequestSort;
import si.opkp.util.RequestWhere;

public class SelectOperation {

	private List<RequestField> fields;
	private String from;
	private List<Graph<String, AstNode>.Edge> joins;
	private RequestWhere where;
	private RequestSort sort;
	private List<RequestField> group;
	private long skip, take;

	public SelectOperation() {
		joins = new ArrayList<>();
		skip = -1;
		take = -1;
	}

	public SelectOperation fields(List<RequestField> fields) {
		this.fields = fields;

		return this;
	}

	public SelectOperation from(String node) {
		from = node;

		return this;
	}

	public SelectOperation join(Graph<String, AstNode>.Edge edge) {
		joins.add(edge);

		return this;
	}

	public SelectOperation where(RequestWhere where) {
		this.where = where;

		return this;
	}

	public SelectOperation sort(RequestSort sort) {
		this.sort = sort;

		return this;
	}

	public SelectOperation group(List<RequestField> fields) {
		group = fields;

		return this;
	}

	public SelectOperation skip(long skip) {
		this.skip = skip;

		return this;
	}

	public SelectOperation take(long take) {
		this.take = take;

		return this;
	}

	public List<RequestField> getFields() {
		return fields;
	}

	public String getFrom() {
		return from;
	}

	public List<Graph<String, AstNode>.Edge> getJoins() {
		return joins;
	}

	public RequestWhere getWhere() {
		return where;
	}

	public RequestSort getSort() {
		return sort;
	}

	public List<RequestField> getGroup() {
		return group;
	}

	public long getSkip() {
		return skip;
	}

	public long getTake() {
		return take;
	}

}
