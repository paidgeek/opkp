package si.opkp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import si.opkp.model.Database;
import si.opkp.util.Pojo;

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

}
