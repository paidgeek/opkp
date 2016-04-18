package si.opkp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import si.opkp.util.RequestParams;

@RestController
@CrossOrigin
@RequestMapping("/v1")
public class Router {

	@Autowired
	private Map<String, Controller> controllers;

	@PostConstruct
	private void init() {
		// remove "Controller" from controller names
		Map<String, Controller> full = controllers;
		controllers = new TreeMap<>();

		for (Map.Entry<String, Controller> e : full.entrySet()) {
			controllers.put(e.getKey()
								  .replace("Controller", ""), e.getValue());
		}
	}

	@RequestMapping(value = "{controller}/{arg1}/{arg2}", method = RequestMethod.GET)
	public ResponseEntity<?> get2(@PathVariable String controller,
											@PathVariable String arg1,
											@PathVariable String arg2,
											RequestParams params) {
		return get(controller, new String[]{arg1, arg2}, params);
	}

	@RequestMapping(value = "{controller}/{arg1}", method = RequestMethod.GET)
	public ResponseEntity<?> get1(@PathVariable String controller,
											@PathVariable String arg1,
											RequestParams params) {
		return get(controller, new String[]{arg1}, params);
	}

	@RequestMapping(value = "{controller}", method = RequestMethod.GET)
	public ResponseEntity<?> get(@PathVariable String controller,
										  RequestParams params) {
		return get(controller, new String[]{}, params);
	}

	public ResponseEntity<?> get(String controller, String[] path, RequestParams params) {
		if (!controllers.containsKey(controller)) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}

		return controllers.get(controller)
								.get(path, params);
	}

}
