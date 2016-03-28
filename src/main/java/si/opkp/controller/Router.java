package si.opkp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import si.opkp.util.Pojo;
import si.opkp.util.RequestParams;
import si.opkp.util.Util;

@RestController
@CrossOrigin
@RequestMapping("/v1")
public class Router {

	@Autowired
	private Map<String, Controller> controllers;

	@RequestMapping(value = "{controller}", method = RequestMethod.GET)
	public ResponseEntity<?> get(@PathVariable("controller") String controllerName,
										  @ModelAttribute RequestParams params) {
		return get(controllerName, "", params);
	}

	@RequestMapping(value = "{controller}/{arguments}", method = RequestMethod.GET)
	public ResponseEntity<?> get(@PathVariable("controller") String controllerName,
										  @PathVariable("arguments") String arguments,
										  @ModelAttribute RequestParams params) {
		try {
			Controller controller = controllers.get(controllerName + "Controller");

			return controller.get(Util.parseQueryArguments(arguments), params);
		} catch (Exception e) {
			return Util.responseError("invalid controller '" + controllerName + "' with arguments '" + arguments + "'", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "{controller}", method = RequestMethod.POST)
	public ResponseEntity<?> post(@PathVariable("controller") String controllerName,
											@ModelAttribute RequestParams params,
											@RequestBody Pojo body) {
		return post(controllerName, "", params, body);
	}

	@RequestMapping(value = "{controller}/{arguments}", method = RequestMethod.POST)
	public ResponseEntity<?> post(@PathVariable("controller") String controllerName,
											@PathVariable("arguments") String arguments,
											@ModelAttribute RequestParams params,
											@RequestBody Pojo body) {
		try {
			Controller controller = controllers.get(controllerName + "Controller");

			return controller.post(Util.parseQueryArguments(arguments), params, body);
		} catch (Exception e) {
			return Util.responseError("invalid controller '" + controllerName + "' with arguments '" + arguments + "'", HttpStatus.BAD_REQUEST);
		}
	}

}
