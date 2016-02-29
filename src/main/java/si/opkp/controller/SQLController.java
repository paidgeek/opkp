package si.opkp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<Pojo> select(@RequestBody SQLSelect select) {
		System.out.println(select.getStatement());

		List<Pojo> objects = db.queryObjects(select.getStatement());

		Pojo result = new Pojo();

		result.setProperty("count", objects.size());
		result.setProperty("objects", objects);

		return ResponseEntity.ok(result);
	}

}
