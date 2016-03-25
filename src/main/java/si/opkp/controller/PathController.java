package si.opkp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import si.opkp.model.Database;
import si.opkp.util.Pojo;
import si.opkp.util.RequestParams;
import si.opkp.util.Util;

@RestController
@RequestMapping("v1/path")
@CrossOrigin
public class PathController {

	@Autowired
	private Database database;
	@Autowired
	private GraphController graphController;

	@RequestMapping("/{start}/{goal}")
	ResponseEntity<Pojo> get(@PathVariable("start") String start,
									 @PathVariable("goal") String goal,
									 @ModelAttribute RequestParams params) {
		return perform(start, goal, params);
	}

	public ResponseEntity<Pojo> perform(String start, String goal, RequestParams params) {
		Optional<List<String>> path = database.getDataGraph()
														  .findPath(start, goal);

		if (!path.isPresent()) {
			return Util.responseError("path not found", HttpStatus.BAD_REQUEST);
		}

		return graphController.perform(String.join(",", path.get()), params);
	}

}
