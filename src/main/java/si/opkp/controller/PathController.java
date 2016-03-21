package si.opkp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import si.opkp.model.DataGraph;
import si.opkp.util.Pojo;
import si.opkp.util.RequestParams;
import si.opkp.util.Util;

@RestController
@RequestMapping("v1/path")
@CrossOrigin
public class PathController {

	@RequestMapping("/{start}/{goals}")
	public ResponseEntity<Pojo> path(@PathVariable("start") String start,
												@PathVariable("goals") String goalsList,
												@ModelAttribute RequestParams params) {
		List<String> goals = Util.parseStringList(goalsList);

		return perform(start, goals, params);
	}

	public ResponseEntity<Pojo> perform(String start, List<String> goals, RequestParams params) {
		List<String> path = DataGraph.getInstance()
											  .findPath(start, goals.get(0));

		if (path == null) {
			return Util.responseError("path not found", HttpStatus.BAD_REQUEST);
		}

		return GraphController.getInstance()
									 .perform(String.join(",", path), params);
	}

}
