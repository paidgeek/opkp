package si.opkp.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import si.opkp.model.*;
import si.opkp.util.*;

import javax.annotation.*;

import java.util.*;
import java.util.stream.*;

@RestController
@RequestMapping("v1/search")
@CrossOrigin
public class SearchController {

	private static SearchController instance;
	@Autowired
	private Database db;

	public static SearchController getInstance() {
		return instance;
	}

	@PostConstruct
	private void init() {
		instance = this;
	}

	@RequestMapping(value = "/{model}", method = RequestMethod.GET)
	public ResponseEntity<Pojo> get(@PathVariable("model") String model,
											  @ModelAttribute RequestParams params) {
		return perform(model, params);
	}

	public ResponseEntity<Pojo> perform(String model, RequestParams params) {
		String[] keywords = params.getKeywords();

		if (keywords.length > 0) {
			keywords[keywords.length - 1] = keywords[keywords.length - 1] + "*";
		}

		Set<String> stopwords = StopWords.getInstance()
													.getStopWords(params.getLanguage());
		String kw = Arrays.asList(keywords)
								.stream()
								.map(String::toLowerCase)
								.filter(word -> !stopwords.contains(word))
								.collect(Collectors.joining(" "));

		Pojo result = new Pojo();
		Pojo meta = new Pojo();
		List<Pojo> objects;
		Integer[] limit = params.getLimit();
		int total = 0;

		// TODO clean up
		if (model.equals("fir_food")) {
			objects = db.queryObjects("CALL search_foods(?, ?, ?)", kw, limit[0], limit[1]);
		} else {
			return Util.responseError("invalid model", HttpStatus.BAD_REQUEST);
		}

		if (!objects.isEmpty()) {
			total = objects.get(0)
								.getInteger("total");
		}


		List<RequestColumn> columns = Arrays.asList(params.getColumns());

		for (Pojo pojo : objects) {
			pojo.getProperties()
				 .entrySet()
				 .removeIf(e -> !columns.stream()
												.anyMatch(col -> e.getKey()
																		.equals(col.getName())));
		}

		meta.setProperty("count", objects.size());
		meta.setProperty("total", total);

		result.setProperty("meta", meta);
		result.setProperty("result", objects);

		return ResponseEntity.ok(result);
	}

}
