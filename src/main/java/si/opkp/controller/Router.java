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

	@RequestMapping(value = "{controller}/{model}", method = RequestMethod.GET)
	public ResponseEntity<Pojo> get(@PathVariable("controller") String controller,
											  @PathVariable("model") String model,
											  @ModelAttribute RequestParams params) {
		if (!controllers.containsKey(controller)) {
			return Util.responseError("controller '" + controller + "' not found", HttpStatus.BAD_REQUEST);
		}

		return controllers.get(controller)
								.get(model.split(","), params);
	}

}
