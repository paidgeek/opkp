package si.opkp.util;

import com.moybl.restql.ast.AstNode;
import com.moybl.restql.ast.Call;
import com.moybl.restql.ast.Identifier;
import com.moybl.restql.ast.Literal;
import com.moybl.restql.ast.Member;
import com.moybl.restql.ast.Sequence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RequestField {

	private String name;
	private String node;
	private boolean isEdge;
	private long skip;
	private long take;
	private List<RequestField> fields;
	private RequestField parent;

	public RequestField(String name) {
		this.name = name;

		node = null;
		fields = Collections.emptyList();
	}

	public RequestField(String name, String node) {
		this.name = name;
		this.node = node;

		fields = Collections.emptyList();
	}

	public RequestField(String name, List<RequestField> fields) {
		this.name = name;
		this.fields = fields;

		node = null;
		isEdge = true;
	}

	public RequestField(String name, String node, List<RequestField> fields, RequestField parent) {
		this.name = name;
		this.node = node;
		this.fields = fields;
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

	public List<RequestField> getFields() {
		return fields;
	}

	public static RequestField fromAst(AstNode ast) {
		if (ast instanceof Identifier) {
			String name = ((Identifier) ast).getName();

			return new RequestField(name);
		} else if (ast instanceof Call) {
			return fromAst(new Member(ast, new Call(new Identifier("fields"), new Sequence(Collections.emptyList()))));
		} else if (ast instanceof Member) {
			Member member = (Member) ast;

			if (member.getTarget() instanceof Identifier && member.getExpression() instanceof Identifier) {
				String node = ((Identifier) member.getTarget()).getName();
				String name = ((Identifier) member.getExpression()).getName();

				return new RequestField(name, node);
			} else if (member.getTarget() instanceof Call) {
				Call call = (Call) member.getTarget();

				if (call.getTarget() instanceof Identifier) {
					String name = ((Identifier) call.getTarget()).getName();
					RequestField rf = new RequestField(name);
					rf.isEdge = true;
					rf.fields = new ArrayList<>();

					AstNode t = null;
					AstNode e = null;

					if (member.getExpression() instanceof Member) {
						Member m = (Member) member.getExpression();

						t = m.getTarget();
						e = m.getExpression();
					} else {
						t = member.getExpression();
					}

					do {
						String paramName = ((Identifier) ((Call) t).getTarget()).getName();
						Sequence paramArgs = ((Call) t).getArguments();

						if (paramName.equalsIgnoreCase("fields")) {
							rf.fields = paramArgs.getElements()
									.stream()
									.map(node -> {
										if (node instanceof Call) {
											node = new Member(node, new Call(new Identifier("fields"), new Sequence(Collections.emptyList())));
										}

										RequestField nf = RequestField.fromAst(node);

										if (nf != null) {
											nf.parent = rf;
										}

										return nf;
									})
									.collect(Collectors.toList());
						} else if (paramName.equalsIgnoreCase("skip")) {
							rf.skip = (long) ((Literal) paramArgs.getElements()
									.get(0)).numberValue();
						} else if (paramName.equalsIgnoreCase("take")) {
							rf.take = (long) ((Literal) paramArgs.getElements()
									.get(0)).numberValue();
						}

						if (e instanceof Member) {
							t = ((Member) e).getTarget();
							e = ((Member) e).getExpression();
						} else if (e instanceof Call) {
							t = e;
							e = null;
						}
					} while (e != null);

					return rf;
				}
			}
		}

		return null;
	}

}
