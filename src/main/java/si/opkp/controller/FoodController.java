package si.opkp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import si.opkp.model.Database;
import si.opkp.util.Pojo;
import si.opkp.util.SQLFactory;
import si.opkp.util.Util;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/v1/foods")
@CrossOrigin
public class FoodController {

	@Autowired
	private Database db;
	@Resource
	private HashMap<String, String> tableNames;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Pojo> getFoods(@RequestParam(name = "fields", required = false, defaultValue = "*") String fields,
	                                     @RequestParam(name = "sort", required = false) String sort,
	                                     @RequestParam(name = "q", required = false) String query,
	                                     @RequestParam(name = "limit", required = false, defaultValue = "0,20") String limit) {

		String[] select = Util.parseFieldList(fields);
		String[] orderBy = Util.parseFieldList(sort);
		int[] boundary = Util.parseLimit(limit);

		SQLFactory.SQLSelectBuilder selectBuilder = SQLFactory.select(select)
				.from(tableNames.get("Food"));

		if (query != null) {
			selectBuilder.where(query);
		}

		if (orderBy != null) {
			selectBuilder.orderByPrefixedColumns(orderBy);
		}

		selectBuilder.limit(boundary)
				.build();

		Pojo result = new Pojo();
		Pojo meta = new Pojo();

		List<Pojo> objects = db.queryObjects(selectBuilder.build());
		meta.setProperty("count", objects.size());

		result.setProperty("meta", meta);
		result.setProperty("result", objects);

		return ResponseEntity.ok(result);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Pojo> getFood(@PathVariable("id") String foodId) {
		return ResponseEntity.ok(null);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Pojo> putFood(@PathVariable("id") String foodId) {
		return ResponseEntity.ok(null);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Pojo> deleteFood(@PathVariable("id") String foodId) {
		return ResponseEntity.ok(null);
	}

}
