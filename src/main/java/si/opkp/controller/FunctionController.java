package si.opkp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import si.opkp.model.Database;
import si.opkp.util.Pojo;
import si.opkp.util.RequestParams;
import si.opkp.util.Util;

@RestController
@CrossOrigin
@RequestMapping("v1/call")
public class FunctionController {

	@Autowired
	private Database database;

	@RequestMapping(value = "/{function}/{arguments}", method = RequestMethod.GET)
	public ResponseEntity<Pojo> call(@PathVariable("function") String function,
												@PathVariable("arguments") String arguments,
												@ModelAttribute RequestParams params) {
		Object[] args = arguments.split(",");

		return Util.createResult(database.callFunction(function, args), 10);
	}

	@RequestMapping(value = "/{function}", method = RequestMethod.GET)
	public ResponseEntity<Pojo> call(@PathVariable("function") String function,
												@ModelAttribute RequestParams params) {
		return call(function, "", params);
	}

}
