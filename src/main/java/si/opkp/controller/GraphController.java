package si.opkp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import si.opkp.model.Database;
import si.opkp.query.ConditionBuilder;
import si.opkp.query.QueryFactory;
import si.opkp.query.SelectBuilder;
import si.opkp.util.DirectedGraph;
import si.opkp.util.Pojo;
import si.opkp.util.RequestParams;
import si.opkp.util.Util;

@RestController
@CrossOrigin
@RequestMapping("v1/graph")
public class GraphController {

	@Autowired
	private Database database;

	@RequestMapping(value = "/{model}", method = RequestMethod.GET)
	public ResponseEntity<Pojo> traverse(String model, RequestParams params) {
		String[] path = model.split(",");

		SelectBuilder selectBuilder = QueryFactory.select()
																.fields(params.getFields())
																.from(path[0]);
		SelectBuilder countBuilder = QueryFactory.select()
															  .from(path[0]);

		for (int i = 1; i < path.length; i++) {
			String a = path[i - 1];
			String b = path[i];

			Optional<DirectedGraph<String, ConditionBuilder>.Edge> edge = database.getDataGraph()
																										 .getEdge(a, b);
			if (edge.isPresent()) {
				selectBuilder.join(b, edge.get()
												  .getValue());
				countBuilder.join(b, edge.get()
												 .getValue());
			} else {
				return Util.responseError(String.format("can not join '%s' and '%s'", a, b), HttpStatus.BAD_REQUEST);
			}
		}

		if (params.getQuery() != null) {
			selectBuilder.where(QueryFactory.condition()
													  .parse(params.getQuery()));
			countBuilder.where(QueryFactory.condition()
													 .parse(params.getQuery()));
		}

		if (params.getGroup() != null) {
			selectBuilder.group(params.getGroup());
		}

		if (params.getSort() != null) {
			selectBuilder.sort(params.getSort());
		}

		selectBuilder.skip(params.getSkip())
						 .take(params.getTake());

		try {
			List<Pojo> objects = database.queryObjects(selectBuilder);
			long total = database.count(countBuilder);

			return Util.createResult(objects, total);
		} catch (Exception e) {
			e.printStackTrace();

			return Util.responseError(HttpStatus.BAD_REQUEST);
		}
	}

}
