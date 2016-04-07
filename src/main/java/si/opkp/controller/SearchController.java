package si.opkp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import si.opkp.model.Database;
import si.opkp.model.NodeResult;
import si.opkp.model.NodeSuccessResult;
import si.opkp.util.Pojo;
import si.opkp.util.RequestParams;
import si.opkp.util.Util;

@RestController
@RequestMapping("/v1/search")
@CrossOrigin
public class SearchController {

	private static final Map<String, String> SEARCH_FUNCTIONS = new HashMap<String, String>() {{
		put("fir_food", "search_foods");
		put("fir_recipe", "search_recipes");
	}};
	private static final String TOTAL_FIELD_NAME = "__total";
	private static final String SCORE_FIELD_NAME = "__score";

	@Autowired
	private Database database;

	@RequestMapping(value = "{node}/{keywordList}", method = RequestMethod.GET)
	public ResponseEntity<?> get(@PathVariable String node,
										  @PathVariable String keywordList,
										  RequestParams params) {
		try {
			String function = SEARCH_FUNCTIONS.get(node);
			String[] keywords = keywordList.split(",");

			for (int i = 0; i < keywords.length; i++) {
				keywords[i] = keywords[i] + "*";
			}

			String keywordParam = String.join(" ", (CharSequence[]) keywords);

			NodeResult result = database.callFunction(function, keywordParam, params.getSkip(), params.getTake());

			if (result instanceof NodeSuccessResult) {
				NodeSuccessResult successResult = (NodeSuccessResult) result;

				List<Pojo> objects = successResult.getObjects();

				if (!objects.isEmpty()) {
					successResult.setTotal(objects.get(0)
							.getLong(TOTAL_FIELD_NAME));
				}

				for (Pojo object : objects) {
					object.removeProperty(TOTAL_FIELD_NAME);
					object.removeProperty(SCORE_FIELD_NAME);
				}
			}

			return result.toResponseEntity();
		} catch (Exception e) {
			return Util.responseError(HttpStatus.BAD_REQUEST);
		}
	}

}