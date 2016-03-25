package si.opkp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import si.opkp.model.Database;
import si.opkp.query.ConditionBuilder;
import si.opkp.query.QueryFactory;
import si.opkp.query.RequestColumn;
import si.opkp.query.SelectBuilder;
import si.opkp.util.DirectedGraph;
import si.opkp.util.Pojo;
import si.opkp.util.RequestParams;
import si.opkp.util.Util;

@RestController
@RequestMapping("v1/graph")
@CrossOrigin
public class GraphController {

	@Autowired
	private Database database;

	@RequestMapping(value = "/{model}", method = RequestMethod.GET)
	ResponseEntity<Pojo> get(@PathVariable("model") String model,
									 @ModelAttribute RequestParams params) {
		return perform(model, params);
	}

	public ResponseEntity<Pojo> perform(String model, RequestParams params) {
		try {
			String[] path = model.split(",");

			Optional<String> err = database.getValidator()
													 .validate(path, params.getColumns(), params.getSort());

			if (err.isPresent()) {
				return Util.responseError(err.get(), HttpStatus.BAD_REQUEST);
			}

			SelectBuilder selectBuilder = QueryFactory.select()
																	.expr(params.getColumns())
																	.from(path[0]);
			SelectBuilder countBuilder = QueryFactory.select()
																  .expr(new RequestColumn("COUNT(*) as count"))
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
				selectBuilder.where(params.getQuery());
				countBuilder.where(params.getQuery());
			}

			if (params.getGroup() != null) {
				selectBuilder.groupBy(params.getGroup());
			}

			if (params.getSort() != null) {
				selectBuilder.orderBy(params.getSort());
			}

			selectBuilder.limit(params.getLimit());

			List<Pojo> objects = database.queryObjects(selectBuilder.build());
			long total = database.queryObject(countBuilder.build())
										.getLong("count");

			return Util.createResult(objects, total);
		} catch (Exception e) {
			e.printStackTrace();

			return Util.responseError(HttpStatus.BAD_REQUEST);
		}
	}

}
