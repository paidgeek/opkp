package si.opkp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import si.opkp.model.Database;
import si.opkp.util.Pojo;

import java.util.List;

@RestController
@RequestMapping("/food")
@CrossOrigin
public class FoodController {

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

	@RequestMapping(value = "/{foodId}", method = RequestMethod.PATCH)
	public ResponseEntity setFood(@PathVariable("foodId") String foodId,
	                              @RequestBody Pojo food) {
		return ResponseEntity.ok(null);
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ResponseEntity<Pojo> findFoods(@RequestParam("keywords") String keywords,
	                                      @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
	                                      @RequestParam(value = "count", required = false, defaultValue = "10") int count) {
		keywords = String.join(" ", keywords.split(","));

		List<Pojo> objects = db.queryObjects("CALL search_foods(?, ?, ?)", keywords, offset, count);
		int total = 0;

		// TODO gahh...
		if (!objects.isEmpty()) {
			total = objects.get(0).getInteger("total");
		}

		objects.forEach(pojo -> {
			pojo.removeProperty("m1");
			pojo.removeProperty("m2");
			pojo.removeProperty("total");
		});

		Pojo result = new Pojo();

		result.setProperty("total", total);
		result.setProperty("objects", objects);

		return ResponseEntity.ok(result);
	}

}
