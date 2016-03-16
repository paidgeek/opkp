package si.opkp.controller;

import com.moybl.restql.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import si.opkp.model.*;
import si.opkp.util.*;

import javax.annotation.*;

import java.util.*;

@RestController
@RequestMapping("v1/crud/{model}")
@CrossOrigin
public class CRUDController {

	private static CRUDController instance;

	public static CRUDController getInstance() {
		return instance;
	}

	@Autowired
	private Database db;

	@PostConstruct
	private void init() {
		instance = this;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Pojo> create(@PathVariable("model") String model,
												  @RequestBody Pojo body) {
		return performCreate(model, body);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Pojo> read(@PathVariable("model") String model,
												@RequestParam(name = "columns", required = false) String columnList,
												@RequestParam(name = "sort", required = false) String sortList,
												@RequestParam(name = "q", required = false) String query,
												@RequestParam(name = "limit", required = false) String limitList) {
		List<String> columns = Util.parseStringList(columnList);
		List<String> sort = Util.parseStringList(sortList);
		List<Integer> limit = Util.parseIntegerList(limitList);

		return performRead(model, columns, query, sort, limit);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Pojo> update(@PathVariable("model") String model,
												  @RequestParam("q") String query,
												  @RequestBody Pojo body) {
		return performUpdate(model, query, body);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<Pojo> delete(@PathVariable("model") String model,
												  @RequestParam("q") String query) {
		return performDelete(model, query);
	}

	public ResponseEntity<Pojo> performCreate(String model, Pojo body) {
		Optional<String> err = Validator.validateFull(model, body);

		if (err.isPresent()) {
			return Util.responseError(err.get(), HttpStatus.BAD_REQUEST);
		}

		SQLInsertBuilder insertBuilder = new SQLInsertBuilder(model);

		body.getProperties().entrySet()
				.forEach(prop -> insertBuilder.insert(prop.getKey(), prop.getValue()));

		try {
			db.update(insertBuilder.build());
		} catch (Exception e) {
			e.printStackTrace();

			return Util.responseError(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		Pojo meta = new Pojo();
		Pojo result = new Pojo();

		result.setProperty("meta", meta);
		result.setProperty("created", body);

		return ResponseEntity.ok(result);
	}

	public ResponseEntity<Pojo> performRead(String model, List<String> columns, String query, List<String> sort, List<Integer> limit) {
		if (columns == null) {
			columns = Util.stringList("*");
		}

		Optional<String> sqlQuery = QueryFactory.select(columns, model, query, sort, limit);
		Optional<String> sqlCount = QueryFactory.count(model, query);

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
	}

	public ResponseEntity<Pojo> performUpdate(String model, String query, Pojo body) {
		Optional<String> err = Validator.validatePartial(model, body);

		if (err.isPresent()) {
			return Util.responseError(err.get(), HttpStatus.BAD_REQUEST);
		}

		SQLUpdateBuilder updateBuilder = new SQLUpdateBuilder(model);

		body.getProperties().entrySet()
				.forEach(prop -> updateBuilder.set(prop.getKey(), prop.getValue()));

		String sqlQuery = RestQL.parseToSQL(query);
		updateBuilder.where(sqlQuery);

		int updated;
		try {
			updated = db.update(updateBuilder.build());
		} catch (Exception e) {
			e.printStackTrace();

			return Util.responseError(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		SQLSelectBuilder selectBuilder = new SQLSelectBuilder("*")
				.from(model)
				.where(sqlQuery);
		List<Pojo> objects = db.queryObjects(selectBuilder.build());

		Pojo meta = new Pojo();
		Pojo result = new Pojo();

		meta.setProperty("updated", updated);

		result.setProperty("meta", meta);
		result.setProperty("changed", objects);

		return ResponseEntity.ok(result);
	}

	public ResponseEntity<Pojo> performDelete(String model, String query) {
		SQLDeleteBuilder deleteBuilder = new SQLDeleteBuilder(model);

		String sqlQuery = RestQL.parseToSQL(query);
		deleteBuilder.where(sqlQuery);

		int deleted;
		try {
			deleted = db.update(deleteBuilder.build());
		} catch (Exception e) {
			e.printStackTrace();

			return Util.responseError(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		Pojo meta = new Pojo();
		Pojo result = new Pojo();

		meta.setProperty("deleted", deleted);

		result.setProperty("meta", meta);

		return ResponseEntity.ok(result);
	}

}
