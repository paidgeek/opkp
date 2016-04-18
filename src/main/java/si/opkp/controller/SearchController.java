package si.opkp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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

@Service
public class SearchController extends Controller {

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