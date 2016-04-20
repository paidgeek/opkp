package si.opkp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import si.opkp.model.Database;
import si.opkp.model.NodeResult;
import si.opkp.util.RequestParams;

@Service
public class SearchController extends ControllerAdapter {

	private static final Map<String, String> SEARCH_FUNCTIONS = new HashMap<String, String>() {{
		put("fir_food", "search_foods");
		put("fir_recipe", "search_recipes");
	}};

	@Autowired
	private Database database;

	@Override
	public ResponseEntity<?> get(String[] path, RequestParams params) {
		try {
			String function = SEARCH_FUNCTIONS.get(path[0]);
			String[] keywords = path[1].split(",");

			for (int i = 0; i < keywords.length; i++) {
				keywords[i] = keywords[i] + "*";
			}

			String keywordParam = String.join(" ", (CharSequence[]) keywords);

			NodeResult result = database.callFunction(function, keywordParam, params.getSkip(), params.getTake());

			return result.toResponseEntity();
		} catch (Exception e) {
			return super.get(path, params);
		}
	}

}