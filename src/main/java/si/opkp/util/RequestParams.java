package si.opkp.util;

import com.moybl.restql.RestQL;
import com.moybl.restql.ast.AstNode;
import com.moybl.restql.ast.Sequence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RequestParams {

	private List<RequestField> fields;
	private RequestWhere where;
	private long skip;
	private long take;

	public RequestParams() {
		fields = new ArrayList<>();
		skip = 0;
		take = 100;
	}

	public void setFields(String fieldList) {
		AstNode node = RestQL.parse(fieldList)
				.getElements()
				.get(0);

		if (node instanceof Sequence) {
			fields = ((Sequence) node).getElements()
					.stream()
					.map(RequestField::fromAst)
					.collect(Collectors.toList());
		} else {
			fields = Collections.singletonList(RequestField.fromAst(node));
		}
	}

	public void setSkip(String skip) {
		this.skip = Long.parseLong(skip);
	}

	public void setTake(String take) {
		this.take = Long.parseLong(take);
	}

	public long getSkip() {
		return skip;
	}

	public long getTake() {
		return take;
	}

	public void setWhere(String condition) {
		AstNode node = RestQL.parse(condition)
				.getElements()
				.get(0);

		this.where = new RequestWhere(node);
	}

	public List<RequestField> getFields() {
		return fields;
	}

	public RequestWhere getWhere() {
		return where;
	}

}