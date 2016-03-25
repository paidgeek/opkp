package si.opkp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import si.opkp.model.Database;
import si.opkp.model.FieldDefinition;
import si.opkp.query.*;
import si.opkp.util.Pojo;
import si.opkp.util.RequestParams;
import si.opkp.util.Util;

@RestController
@RequestMapping("v1/crud/{model}")
@CrossOrigin
public class CRUDController {

	@Autowired
	private Database database;

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<Pojo> create(@PathVariable("model") String model,
										 @RequestBody Pojo body) {
		return performCreate(model, body);
	}

	@RequestMapping(method = RequestMethod.GET)
	ResponseEntity<Pojo> read(@PathVariable("model") String model,
									  @ModelAttribute RequestParams params) {

		return performRead(model, params);
	}

	@RequestMapping(method = RequestMethod.PUT)
	ResponseEntity<Pojo> update(@PathVariable("model") String model,
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
		Optional<String> err = database.getValidator()
												 .validateFull(model, body);

		if (err.isPresent()) {
			return Util.responseError(err.get(), HttpStatus.BAD_REQUEST);
		}

		InsertBuilder insertBuilder = QueryFactory.insert()
																.into(model);
		body.getProperties()
			 .entrySet()
			 .forEach(prop -> insertBuilder.value(prop.getKey(), prop.getValue()));

		SelectBuilder selectBuilder = QueryFactory.select()
																.expr(RequestColumn.columnAll())
																.from(model);
		ConditionBuilder conditionBuilder = QueryFactory.condition();
		Iterator<FieldDefinition> i = database.getDefinition(model)
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
			database.update(insertBuilder.build());
			created = database.queryObject(selectBuilder.build());
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

		List<Pojo> objects = database.queryObjects(selectBuilder.build());
		long total = database.queryObject(countBuilder.build())
									.getLong("count");

		return Util.createResult(objects, total);
	}

	public ResponseEntity<Pojo> performUpdate(String model, RequestParams params, Pojo body) {
		Optional<String> err = database.getValidator()
												 .validatePartial(model, body);

		if (err.isPresent()) {
			return Util.responseError(err.get(), HttpStatus.BAD_REQUEST);
		}

		UpdateBuilder updateBuilder = QueryFactory.update()
																.from(model);

		body.getProperties()
			 .entrySet()
			 .forEach(prop -> updateBuilder.set(new RequestColumn(prop.getKey()), prop.getValue()));

		updateBuilder.where(params.getQuery());

		long total;
		try {
			total = database.update(updateBuilder.build());
		} catch (Exception e) {
			e.printStackTrace();

			return Util.responseError(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		SelectBuilder selectBuilder = QueryFactory.select()
																.expr(params.getColumns())
																.from(model)
																.where(params.getQuery());
		List<Pojo> objects = database.queryObjects(selectBuilder.build());

		return Util.createResult(objects, total);
	}

	public ResponseEntity<Pojo> performDelete(String model, RequestParams params) {
		DeleteBuilder deleteBuilder = QueryFactory.delete()
																.from(model)
																.where(params.getQuery());
		long total;

		try {
			total = database.update(deleteBuilder.build());
		} catch (Exception e) {
			e.printStackTrace();

			return Util.responseError(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return Util.createResult(Collections.emptyList(), total);
	}

}
