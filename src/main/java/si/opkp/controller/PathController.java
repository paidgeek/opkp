package si.opkp.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import si.opkp.model.*;
import si.opkp.util.*;

import javax.annotation.*;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/v1/path")
public class PathController {

	private static PathController instance;
	@Autowired
	private Database db;

	public static PathController getInstance() {
		return instance;
	}

	@PostConstruct
	private void init() {
		instance = this;
	}

	@RequestMapping(value = "/{model}", method = RequestMethod.GET)
	public ResponseEntity<Pojo> get(@PathVariable("model") String model,
											  @RequestParam(name = "columns", required = false) String columnList,
											  @RequestParam(name = "sort", required = false) String sortList,
											  @RequestParam(name = "q", required = false) String query,
											  @RequestParam(name = "limit", required = false) String limitList) {
		List<String> columns = Util.parseStringList(columnList);
		List<String> sort = Util.parseStringList(sortList);
		List<Integer> limit = Util.parseIntegerList(limitList);

		return perform(model, columns, query, sort, limit);
	}

	public ResponseEntity<Pojo> perform(String model, List<String> columns, String query, List<String> sort, List<Integer> limit) {
		try {
			if (columns == null || columns.isEmpty()) {
				columns = Util.stringList("*");
			}

			List<String> path = Arrays.asList(model.split("><|<>|<|>"));

			for (String node : path) {
				model = model.replaceAll(node, ",");
			}

			List<String> joins = Arrays.asList(model.substring(1).split(","));

			Optional<String> sqlQuery = QueryFactory.select(columns, path, joins, query, sort, limit);
			Optional<String> sqlCount = QueryFactory.count(path, joins, query);

			if (!(sqlCount.isPresent() && sqlQuery.isPresent())) {
				return Util.responseError("invalid model path", HttpStatus.BAD_REQUEST);
			}

			List<Pojo> objects = db.queryObjects(sqlQuery.get());
			long total = db.queryObject(sqlCount.get()).getLong("count");

			Pojo result = new Pojo();
			Pojo meta = new Pojo();

			meta.setProperty("count", objects.size());
			meta.setProperty("total", total);

			result.setProperty("meta", meta);
			result.setProperty("result", objects);

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			e.printStackTrace();

			return Util.responseError(HttpStatus.BAD_REQUEST);
		}
	}

}
