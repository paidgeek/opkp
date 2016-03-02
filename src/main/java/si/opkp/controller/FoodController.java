package si.opkp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import si.opkp.model.Database;
import si.opkp.util.Pojo;

import java.util.*;

@RestController
@RequestMapping("/food")
@CrossOrigin
public class FoodController {

	private static final String FOOD_TABLE = "fir_food";

	@Autowired
	private Database db;

	@Autowired
	private String sqlGetFood;
	@Autowired
	private String sqlGetComponentsForFood;

	@RequestMapping(value = "/{foodId}", method = RequestMethod.GET)
	public ResponseEntity<Pojo> getFood(@PathVariable("foodId") String foodId) {
		Pojo food = db.queryObject(sqlGetFood, foodId);

		if (food == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}

		food.setProperty("components", db.queryObjects(sqlGetComponentsForFood, foodId));

		return ResponseEntity.ok(food);
	}

	@RequestMapping(value = "/update/{foodId}", method = RequestMethod.POST)
	public ResponseEntity updateFood(@PathVariable("foodId") String foodId,
	                                 @RequestBody Pojo delta) {

		String sql = "UPDATE fir_food SET ";

		List<Map.Entry<String, Object>> changes = new ArrayList<>();
		changes.addAll(delta.getProperties().entrySet());

		for (Map.Entry<String, Object> e : changes) {
			sql += e.getKey();
			sql += " = ";

			if (e.getValue() instanceof String) {
				sql += "'" + e.getValue() + "'";
			} else {
				sql += e.getValue();
			}

			sql += ",";
		}

		sql = sql.substring(0, sql.length() - 1);
		sql += " WHERE ORIGFDCD=?";
		int changed = db.update(sql, foodId);

		return ResponseEntity.ok(changed);
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ResponseEntity<Pojo> findFoods(@RequestParam("keywords") String keywords,
	                                      @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
	                                      @RequestParam(value = "count", required = false, defaultValue = "10") int count) {
		String match = "";

		for (String w : keywords.split(",")) {
			match += w + "*|";
		}
		match = match.substring(0, match.length() - 1);

		List<Pojo> objects = db.queryObjects("CALL search_foods(?, ?, ?)", match, offset, count);
		int total = 0;

		if (!objects.isEmpty()) {
			total = objects.get(0).getInteger("total");
		}

		objects.forEach(pojo -> {
			pojo.removeProperty("score");
			pojo.removeProperty("total");
		});

		Pojo result = new Pojo();

		result.setProperty("total", total);
		result.setProperty("objects", objects);

		return ResponseEntity.ok(result);
	}

}
