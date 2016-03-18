package si.opkp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import si.opkp.model.DataGraph;
import si.opkp.util.Pojo;
import si.opkp.util.QueryFactory;
import si.opkp.util.Util;

@RestController
@RequestMapping("v1/path")
@CrossOrigin
public class PathController {

	@RequestMapping("/{start}/{goals}")
	public ResponseEntity<Pojo> path(@PathVariable("start") String start,
												@PathVariable("goals") String goalsList,
												@RequestParam(name = "columns", required = false) String columnList,
												@RequestParam(name = "sort", required = false) String sortList,
												@RequestParam(name = "q", required = false) String query,
												@RequestParam(name = "limit", required = false) String limitList) {
		List<String> goals = Util.parseStringList(goalsList);
		List<String> columns = Util.parseStringList(columnList);
		List<String> sort = Util.parseStringList(sortList);
		List<Integer> limit = Util.parseIntegerList(limitList);

		return perform(start, goals, columns, query, sort, limit);
	}

	public ResponseEntity<Pojo> perform(String start, List<String> goals, List<String> columns, String query, List<String> sort, List<Integer> limit) {
		List<String> path = DataGraph.getInstance().findPath(start, goals.get(0));

		if (path == null) {
			return Util.responseError("path not found", HttpStatus.BAD_REQUEST);
		}

		return GraphController.getInstance().perform(String.join(",", path), columns, query, sort, limit);
	}

}
