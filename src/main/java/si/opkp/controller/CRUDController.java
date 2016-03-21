package si.opkp.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.dao.DuplicateKeyException;
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
												@ModelAttribute RestDto params) {

		return performRead(model, params);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Pojo> update(@PathVariable("model") String model,
												  @ModelAttribute RestDto params,
												  @RequestBody Pojo body) {
		return performUpdate(model, params, body);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<Pojo> delete(@PathVariable("model") String model,
												  @ModelAttribute RestDto params) {
		return performDelete(model, params);
	}

	public ResponseEntity<Pojo> performCreate(String model, Pojo body) {
		Optional<String> err = Validator.validateFull(model, body);

		if (err.isPresent()) {
			return Util.responseError(err.get(), HttpStatus.BAD_REQUEST);
		}

		SQLInsertBuilder insertBuilder = new SQLInsertBuilder(model);
		body.getProperties().entrySet()
				.forEach(prop -> insertBuilder.insert(prop.getKey(), prop.getValue()));

		SQLSelectBuilder selectBuilder = new SQLSelectBuilder("*")
				.from(model);
		SQLConditionBuilder conditionBuilder = new SQLConditionBuilder();
		Iterator<FieldDefinition> i = DataDefinition.getInstance()
				.getDefinition(model)
				.getPrimaryKeys()
				.iterator();

		if (i.hasNext()) {
			FieldDefinition pk = i.next();

			conditionBuilder.equal(pk, body.getProperty(pk.getName()));

			if (i.hasNext()) {
				conditionBuilder.and();
			}
		}

		selectBuilder.where(conditionBuilder.build());

		Pojo created;

		try {
			db.update(insertBuilder.build());
			created = db.queryObject(selectBuilder.build());
		} catch (Exception e) {
			e.printStackTrace();

			if (e instanceof DuplicateKeyException) {
				return Util.responseError("duplicate entry", HttpStatus.BAD_REQUEST);
			}

			return Util.responseError(HttpStatus.BAD_REQUEST);
		}

		Pojo meta = new Pojo();
		Pojo result = new Pojo();

		result.setProperty("meta", meta);
		result.setProperty("created", created);

		return ResponseEntity.ok(result);
	}

	public ResponseEntity<Pojo> performRead(String model, RestDto params) {
		DataGraph dg = DataGraph.getInstance();
		SQLSelectBuilder selectBuilder = new SQLSelectBuilder(params.getColumns())
				.from(model);
		SQLSelectBuilder countBuilder = new SQLSelectBuilder("COUNT(*) as count")
				.from(model);

		if (params.getQuery() != null) {
			selectBuilder.where(params.getQuery());
			countBuilder.where(params.getQuery());
		}

		if (params.getSort() != null) {
			selectBuilder.orderByPrefixedColumns(params.getSort());
		}

		selectBuilder.limit(params.getLimit());

		List<Pojo> objects = db.queryObjects(selectBuilder.build());
		long total = db.queryObject(countBuilder.build()).getLong("count");

		Pojo result = new Pojo();
		Pojo meta = new Pojo();

		meta.setProperty("count", objects.size());
		meta.setProperty("total", total);

		result.setProperty("meta", meta);
		result.setProperty("result", objects);

		return ResponseEntity.ok(result);
	}

	public ResponseEntity<Pojo> performUpdate(String model, RestDto params, Pojo body) {
		Optional<String> err = Validator.validatePartial(model, body);

		if (err.isPresent()) {
			return Util.responseError(err.get(), HttpStatus.BAD_REQUEST);
		}

		SQLUpdateBuilder updateBuilder = new SQLUpdateBuilder(model);

		body.getProperties().entrySet()
				.forEach(prop -> updateBuilder.set(prop.getKey(), prop.getValue()));

		updateBuilder.where(params.getQuery());

		int updated;
		try {
			updated = db.update(updateBuilder.build());
		} catch (Exception e) {
			e.printStackTrace();

			return Util.responseError(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		SQLSelectBuilder selectBuilder = new SQLSelectBuilder("*")
				.from(model)
				.where(params.getQuery());
		List<Pojo> objects = db.queryObjects(selectBuilder.build());

		Pojo meta = new Pojo();
		Pojo result = new Pojo();

		meta.setProperty("updated", updated);

		result.setProperty("meta", meta);
		result.setProperty("changed", objects);

		return ResponseEntity.ok(result);
	}

	public ResponseEntity<Pojo> performDelete(String model, RestDto params) {
		SQLDeleteBuilder deleteBuilder = new SQLDeleteBuilder(model);

		deleteBuilder.where(params.getQuery());

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
