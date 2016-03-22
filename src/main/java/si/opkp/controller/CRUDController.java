package si.opkp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import si.opkp.model.DataDefinition;
import si.opkp.model.DataGraph;
import si.opkp.model.Database;
import si.opkp.model.FieldDefinition;
import si.opkp.model.Validator;
import si.opkp.query.ConditionBuilder;
import si.opkp.query.DeleteBuilder;
import si.opkp.query.InsertBuilder;
import si.opkp.query.QueryFactory;
import si.opkp.query.SelectBuilder;
import si.opkp.query.UpdateBuilder;
import si.opkp.util.Pojo;
import si.opkp.util.RequestColumn;
import si.opkp.util.RequestParams;
import si.opkp.util.Util;

@RestController
@RequestMapping("v1/crud/{model}")
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
												@ModelAttribute RequestParams params) {

		return performRead(model, params);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Pojo> update(@PathVariable("model") String model,
												  @ModelAttribute RequestParams params,
												  @RequestBody Pojo body) {
		return performUpdate(model, params, body);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<Pojo> delete(@PathVariable("model") String model,
												  @ModelAttribute RequestParams params) {
		return performDelete(model, params);
	}

	public ResponseEntity<Pojo> performCreate(String model, Pojo body) {
		Optional<String> err = Validator.validateFull(model, body);

		if (err.isPresent()) {
			return Util.responseError(err.get(), HttpStatus.BAD_REQUEST);
		}

		InsertBuilder insertBuilder = QueryFactory.insert()
																.into(model);
		body.getProperties()
			 .entrySet()
			 .forEach(prop -> insertBuilder.value(prop.getKey(), prop.getValue()));

		SelectBuilder selectBuilder = QueryFactory.select()
																.expr(new RequestColumn("*"))
																.from(model);
		ConditionBuilder conditionBuilder = QueryFactory.condition();
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

		selectBuilder.where(conditionBuilder);

		Pojo created;

		try {
			db.update(insertBuilder.build());
			created = db.queryObject(selectBuilder.build());
		} catch (Exception e) {
			e.printStackTrace();

			if (e instanceof DuplicateKeyException) {
				return Util.responseError("duplicate value", HttpStatus.BAD_REQUEST);
			}

			return Util.responseError(HttpStatus.BAD_REQUEST);
		}

		Pojo meta = new Pojo();
		Pojo result = new Pojo();

		result.setProperty("meta", meta);
		result.setProperty("created", created);

		return ResponseEntity.ok(result);
	}

	public ResponseEntity<Pojo> performRead(String model, RequestParams params) {
		DataGraph dg = DataGraph.getInstance();
		SelectBuilder selectBuilder = QueryFactory.select()
																.expr(params.getColumns())
																.from(model);
		SelectBuilder countBuilder = QueryFactory.select()
															  .expr(new RequestColumn("COUNT(*) as count"))
															  .from(model);

		if (params.getQuery() != null) {
			selectBuilder.where(params.getQuery());
			countBuilder.where(params.getQuery());
		}

		if (params.getSort() != null) {
			selectBuilder.orderBy(params.getSort());
		}

		selectBuilder.limit(params.getLimit());

		List<Pojo> objects = db.queryObjects(selectBuilder.build());
		long total = db.queryObject(countBuilder.build())
							.getLong("count");

		Pojo result = new Pojo();
		Pojo meta = new Pojo();

		meta.setProperty("count", objects.size());
		meta.setProperty("total", total);

		result.setProperty("meta", meta);
		result.setProperty("result", objects);

		return ResponseEntity.ok(result);
	}

	public ResponseEntity<Pojo> performUpdate(String model, RequestParams params, Pojo body) {
		Optional<String> err = Validator.validatePartial(model, body);

		if (err.isPresent()) {
			return Util.responseError(err.get(), HttpStatus.BAD_REQUEST);
		}

		UpdateBuilder updateBuilder = QueryFactory.update()
																.from(model);

		body.getProperties()
			 .entrySet()
			 .forEach(prop -> updateBuilder.set(new RequestColumn(prop.getKey()), prop.getValue()));

		updateBuilder.where(params.getQuery());

		long updated;
		try {
			updated = db.update(updateBuilder.build());
		} catch (Exception e) {
			e.printStackTrace();

			return Util.responseError(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		SelectBuilder selectBuilder = QueryFactory.select()
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

	public ResponseEntity<Pojo> performDelete(String model, RequestParams params) {
		DeleteBuilder deleteBuilder = QueryFactory.delete()
																.from(model)
																.where(params.getQuery());

		long deleted;
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
