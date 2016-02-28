package si.opkp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import si.opkp.model.Database;
import si.opkp.model.OPKPDatabase;
import si.opkp.util.Pojo;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class FoodController {

	private static final int PAGE_LENGTH = 50;
	private static final String SELECT_ALL_FOODS = "SELECT * FROM fir_food LIMIT " + PAGE_LENGTH;

	@Autowired
	private Database db;

	@RequestMapping(value = "/food", method = RequestMethod.GET)
	public Pojo getAllFoods() {
		Pojo result = new Pojo();
		List<Pojo> objects = db.queryObjects(SELECT_ALL_FOODS);

		result.setProperty("count", objects.size());
		result.setProperty("objects", objects);

		return result;
	}

}
