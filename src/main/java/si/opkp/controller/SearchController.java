package si.opkp.controller;

import com.moybl.restql.ast.AstNode;
import com.moybl.restql.ast.Call;
import com.moybl.restql.ast.Identifier;
import com.moybl.restql.ast.Literal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import si.opkp.model.Database;
import si.opkp.model.QueryResult;
import si.opkp.util.Pojo;
import si.opkp.util.RequestParams;
import si.opkp.util.Util;

@Component
public class SearchController implements Controller {

	private static final Map<String, String> SEARCH_FUNCTIONS = new HashMap<String, String>() {{
		put("fir_food", "search_foods");
		put("fir_recipe", "search_recipes");
	}};
	private static final String TOTAL_FIELD_NAME = "__total";
	private static final String SCORE_FIELD_NAME = "__score";

	@Autowired
	private Database database;

	@Override
	public ResponseEntity<?> get(List<AstNode> arguments, RequestParams params) {
		try {
			Call call = (Call) arguments.get(0);

			String function = SEARCH_FUNCTIONS.get(((Identifier) call.getTarget()).getName());
			String[] keywords = ((Literal) call.getArguments()
														  .getElements()
														  .get(0)).stringValue()
																	 .split(",");

			for (int i = 0; i < keywords.length; i++) {
				keywords[i] = keywords[i] + "*";
			}

			String keywordParam = String.join(" ", keywords);

			QueryResult result = database.callFunction(function, keywordParam, params.getSkip(), params.getTake());

			if (result.getStatus() == HttpStatus.OK) {
				List<Pojo> objects = result.getObjects();

				if (!objects.isEmpty()) {
					result.setTotal(objects.get(0)
												  .getLong(TOTAL_FIELD_NAME));
				}

				for (Pojo object : objects) {
					object.removeProperty(TOTAL_FIELD_NAME);
					object.removeProperty(SCORE_FIELD_NAME);
				}
			}

			return new ResponseEntity<>(result, result.getStatus());
		} catch (Exception e) {
			return Util.responseError(HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<?> post(List<AstNode> arguments, RequestParams params, Pojo body) {
		return Util.responseError(HttpStatus.BAD_REQUEST);
	}

}
