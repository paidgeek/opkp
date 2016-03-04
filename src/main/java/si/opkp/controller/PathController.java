package si.opkp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import si.opkp.model.Database;
import si.opkp.util.Pojo;
import si.opkp.util.QueryFactory;
import si.opkp.util.Util;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/v1/path")
public class PathController {

	private static PathController instance;

	public static PathController getInstance() {
		return instance;
	}

	@Autowired
	private Database db;

	@PostConstruct
	private void init() {
		instance = this;
	}

	@RequestMapping(value = "/{model}", method = RequestMethod.GET)
	public ResponseEntity<Pojo> get(@PathVariable("model") String model,
	                                @RequestParam(name = "columns", required = false, defaultValue = "*") String columnList,
	                                @RequestParam(name = "sort", required = false) String sortList,
	                                @RequestParam(name = "q", required = false) String query,
	                                @RequestParam(name = "limit", required = false) String limitList) {
		List<String> columns = Util.parseStringList(columnList);
		List<String> sort = Util.parseStringList(sortList);
		List<Long> limit = Util.parseLongList(limitList);

		return perform(model, columns, query, sort, limit);
	}

	public ResponseEntity<Pojo> perform(String model, List<String> columns, String query, List<String> sort, List<Long> limit) {
		try {
			List<String> path = Arrays.asList(model.split("><|<>|<|>"));

			for (int i = 0; i < path.size(); i++) {
				model = model.replaceAll(path.get(i), ",");
			}

			List<String> joins = Arrays.asList(model.substring(1).split(","));

			String sqlQuery = QueryFactory.select(columns, path, joins, query, sort, limit);
			String sqlCount = QueryFactory.count(path, joins, query);

			if (sqlQuery == null || sqlCount == null) {
				return Util.responseError("invalid model path", HttpStatus.BAD_REQUEST);
			}

			List<Pojo> objects = db.queryObjects(sqlQuery);
			long total = db.queryObject(sqlCount).getLong("count");

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
