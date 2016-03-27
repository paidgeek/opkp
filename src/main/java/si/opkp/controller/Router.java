package si.opkp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
	private ApplicationContext applicationContext;
	@Autowired
	private Map<String, Controller> controllers;

	@RequestMapping(value = "{controller}/{model}", method = RequestMethod.GET)
	public ResponseEntity<Pojo> get(@PathVariable("controller") String controllerName,
											  @PathVariable("model") String modelList,
											  @ModelAttribute RequestParams params) {
		if (!controllers.containsKey(controllerName)) {
			return Util.responseError("controller '" + controllerName + "' not found", HttpStatus.BAD_REQUEST);
		}

		String[] model = modelList.split(",");
		Controller controller = controllers.get(controllerName);
		Use use = controller.getClass()
								  .getAnnotation(Use.class);

		if (use != null) {
			Class<? extends Middleware>[] middlewares = use.classes();

			for (int i = 0; i < middlewares.length; i++) {
				ResponseEntity response = applicationContext.getBean(middlewares[i])
																		  .get(model, params);

				if (response != null) {
					return response;
				}
			}
		}

		return controller.get(model, params);
	}

}
