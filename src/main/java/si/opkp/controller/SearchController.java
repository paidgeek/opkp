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
											  @ModelAttribute RestDto params) {
		return perform(model, params);
	}

	public ResponseEntity<Pojo> perform(String model, RestDto params) {
		List<String> keywords = params.getKeywords();

		if (!keywords.isEmpty()) {
			keywords.set(keywords.size() - 1, keywords.get(keywords.size() - 1) + "*");
		}

		Set<String> stopwords = StopWords.getInstance().getStopWords(params.getLanguage());
		String kw = keywords.stream()
				.map(String::toLowerCase)
				.filter(word -> !stopwords.contains(word))
				.collect(Collectors.joining(" "));

		Pojo result = new Pojo();
		Pojo meta = new Pojo();
		List<Pojo> objects;
		List<Integer> limit = params.getLimit();
		List<String> columns = params.getColumns();
		int offset = 0, count = Integer.MAX_VALUE, total = 0;

		if (limit != null) {
			if (limit.size() == 1) {
				count = limit.get(0);
			} else if (limit.size() == 2) {
				offset = limit.get(0);
				count = limit.get(1);
			}
		}

		// TODO clean up
		if (model.equals("fir_food")) {
			objects = db.queryObjects("CALL search_foods(?, ?, ?)", kw, offset, count);
		} else {
			return Util.responseError("invalid model", HttpStatus.BAD_REQUEST);
		}

		if (!objects.isEmpty()) {
			total = objects.get(0).getInteger("total");
		}

		if (!(columns.size() == 1 && columns.contains("*"))) {
			for (Pojo pojo : objects) {
				pojo.getProperties().entrySet().removeIf(e -> !columns.contains(e.getKey()));
			}
		}

		meta.setProperty("count", objects.size());
		meta.setProperty("total", total);

		result.setProperty("meta", meta);
		result.setProperty("result", objects);

		return ResponseEntity.ok(result);
	}

}
