package si.opkp.controller;

import com.moybl.restql.RestQL;
import com.moybl.restql.Token;
import com.moybl.restql.ast.AstNode;
import com.moybl.restql.ast.Literal;
import com.moybl.restql.ast.Member;
import com.moybl.restql.ast.Sequence;
import com.moybl.restql.factory.RestQLBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import si.opkp.model.Database;
import si.opkp.model.FieldDefinition;
import si.opkp.model.NodeResult;
import si.opkp.query.SelectOperation;
import si.opkp.util.RequestField;
import si.opkp.util.RequestParams;
import si.opkp.util.RequestWhere;

@Service
public class GraphController extends Controller {

	@Autowired
	private Database database;

	@Override
	public ResponseEntity<?> get(String[] path, RequestParams params) {
		if (path.length == 1) {
			String node = path[0];

			NodeResult result = database.query(new SelectOperation().fields(params.getFields())
																					  .from(node)
																					  .where(params.getWhere()));

			return result.toResponseEntity();
		} else if (path.length == 2) {
			String node = path[0];
			Sequence id = (Sequence) RestQL.parse(path[1])
													 .getElements()
													 .get(0);

			List<Literal> ids = id.getElements()
										 .stream()
										 .map(element -> (Literal) element)
										 .collect(Collectors.toList());

			RestQLBuilder b = new RestQLBuilder();
			AstNode condition = null;
			int i = 0;

			for (FieldDefinition identifier : database.getNodes()
																	.get(node)
																	.getIdentifiers()) {
				Member left = b.member(b.identifier(identifier.getNode()), b.identifier(identifier.getName()));

				if (i >= ids.size()) {
					break;
				}

				Literal right = ids.get(i++);

				if (condition == null) {
					condition = b.sequence(b.binaryOperation(left, Token.EQUAL, right));
				} else {
					condition = b.binaryOperation(condition, Token.AND, b.binaryOperation(left, Token.EQUAL, right));
				}
			}

			setMissingNodeValues(node, params.getFields());

			RequestWhere where = new RequestWhere(condition);
			NodeResult result = database.query(new SelectOperation().from(node)
																					  .fields(params.getFields())
																					  .skip(params.getSkip())
																					  .take(params.getTake())
																					  .where(where));

			return result.toResponseEntity();
		}

		return super.get(path, params);
	}

	private void setMissingNodeValues(String node, List<RequestField> fields) {
		fields.stream()
				.filter(field -> field.getNode() == null)
				.forEach(field -> {
					if (field.isEdge()) {
						setMissingNodeValues(field.getName(), field.getFields());
					} else {
						field.setNode(node);
					}
				});
	}

}
