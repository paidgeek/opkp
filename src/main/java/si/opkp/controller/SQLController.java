package si.opkp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import si.opkp.model.Database;
import si.opkp.model.SQLSelect;
import si.opkp.util.Pojo;

import java.util.List;

@RestController
@CrossOrigin
public class SQLController {

	@Autowired
	private Database db;

	@RequestMapping(value = "/select", method = RequestMethod.POST)
	public Object select(@RequestBody SQLSelect select) {
		List<Pojo> objects = db.queryObjects(select.getStatement());

		return new Pojo.Builder()
				.setProperty("count", objects.size())
				.setProperty("objects", objects)
				.build();
	}

}
