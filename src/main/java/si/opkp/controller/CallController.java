package si.opkp.controller;

import com.moybl.restql.ast.AstNode;
import com.moybl.restql.ast.Call;
import com.moybl.restql.ast.Identifier;
import com.moybl.restql.ast.Literal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

import si.opkp.model.Database;
import si.opkp.util.Pojo;
import si.opkp.util.RequestParams;
import si.opkp.util.Util;

@Component
public class CallController implements Controller {

	@Autowired
	private Database database;

	@Override
	public ResponseEntity<?> get(List<AstNode> arguments, RequestParams params) {
		Call call = (Call) arguments.get(0);

		String function = ((Identifier) call.getTarget()).getName();
		Object[] args = new Object[call.getArguments()
												 .getElements()
												 .size()];

		for (int i = 0; i < args.length; i++) {
			AstNode a = call.getArguments()
								 .getElements()
								 .get(i);

			if (a instanceof Literal) {
				Literal val = (Literal) a;

				switch (val.getType()) {
					case NUMBER:
						args[i] = val.numberValue();
						break;
					case STRING:
						args[i] = val.stringValue();
						break;
				}
			} else {
				return Util.responseError("function argument '" + a + "' is not a literal", HttpStatus.BAD_REQUEST);
			}
		}

		return ResponseEntity.ok(database.callFunction(function, args));
	}

	@Override
	public ResponseEntity<?> post(List<AstNode> arguments, RequestParams params, Pojo body) {
		return Util.responseError(HttpStatus.BAD_REQUEST);
	}

}
