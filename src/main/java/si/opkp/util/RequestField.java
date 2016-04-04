package si.opkp.util;

import com.moybl.restql.ast.AstNode;
import com.moybl.restql.ast.Call;
import com.moybl.restql.ast.Identifier;
import com.moybl.restql.ast.Member;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RequestField {

	private String name;
	private String node;
	private boolean isEdge;
	private RequestField parent;
	private List<RequestField> nestedFields;

	public RequestField(String name) {
		this.name = name;

		node = null;
		nestedFields = Collections.emptyList();
	}

	public RequestField(String name, String node) {
		this.name = name;
		this.node = node;

		nestedFields = Collections.emptyList();
	}

	public RequestField(String name, List<RequestField> nestedFields) {
		this.name = name;
		this.nestedFields = nestedFields;
		this.parent = parent;

		node = null;
		isEdge = true;
	}

	public RequestField(String name, String node, List<RequestField> nestedFields, RequestField parent) {
		this.name = name;
		this.node = node;
		this.nestedFields = nestedFields;
		this.parent = parent;

		isEdge = true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public boolean isEdge() {
		return isEdge;
	}

	public RequestField getParent() {
		return parent;
	}

	public List<RequestField> getNestedFields() {
		return nestedFields;
	}

	public static RequestField fromAst(AstNode ast) {
		if (ast instanceof Identifier) {
			String name = ((Identifier) ast).getName();

			return new RequestField(name);
		} else if (ast instanceof Member) {
			Member member = (Member) ast;

			if (member.getTarget() instanceof Identifier && member.getExpression() instanceof Identifier) {
				String node = ((Identifier) member.getTarget()).getName();
				String name = ((Identifier) member.getExpression()).getName();

				return new RequestField(name, node);
			}
		} else if (ast instanceof Call) {
			Call call = (Call) ast;

			if (call.getTarget() instanceof Identifier) {
				String name = ((Identifier) call.getTarget()).getName();
				RequestField rf = new RequestField(name);

				rf.nestedFields = call.getArguments()
											 .getElements()
											 .stream()
											 .map(node -> {
												 RequestField nf = RequestField.fromAst(node);
												 nf.parent = rf;

												 return nf;
											 })
											 .collect(Collectors.toList());
				rf.isEdge = true;

				return rf;
			}
		}

		return null;
	}

}
