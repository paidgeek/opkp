package si.opkp.controller.opkp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import si.opkp.model.Database;
import si.opkp.model.QueryResult;
import si.opkp.util.Pojo;
import si.opkp.util.RequestParams;
import si.opkp.util.Util;

@RestController
@CrossOrigin
@RequestMapping("v1/opkp/search")
public class SearchController {

	private static final Map<String, String> SEARCH_FUNCTIONS = new HashMap<String, String>() {{
		put("fir_food", "search_foods");
	}};
	private static final String TOTAL_FIELD_NAME = "__total";
	private static final String SCORE_FIELD_NAME = "__score";

	@Autowired
	private Database database;

	@RequestMapping("{node}/{keywords}")
	public ResponseEntity<?> get(@PathVariable("node") String node,
										  @PathVariable("keywords") String keywordList,
										  @ModelAttribute RequestParams params) {
		try {
			String[] keywords = keywordList.split(",");

			for (int i = 0; i < keywords.length; i++) {
				keywords[i] = "*" + keywords[i] + "*";
			}

			String function = SEARCH_FUNCTIONS.get(node);
			String keywordParam = String.join(" ", (CharSequence[]) keywords);

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
			return Util.responseError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
