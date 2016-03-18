package si.opkp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import si.opkp.util.Pojo;
import si.opkp.util.Util;

@RestController
@RequestMapping("v1/path")
@CrossOrigin
public class PathController {

	@RequestMapping("/{start}/{goals}")
	public ResponseEntity<Pojo> path(@PathVariable("start") String start,
												@PathVariable("goals") String goalsList) {
		List<String> goals = Util.parseStringList(goalsList);

		return perform(start, goals);
	}

	public ResponseEntity<Pojo> perform(String start, List<String> goals) {


		return ResponseEntity.ok(null);
	}

}
