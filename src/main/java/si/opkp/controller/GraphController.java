package si.opkp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import si.opkp.model.DataGraph;
import si.opkp.model.Database;
import si.opkp.model.Validator;
import si.opkp.query.QueryFactory;
import si.opkp.query.SelectBuilder;
import si.opkp.util.Pojo;
import si.opkp.query.RequestColumn;
import si.opkp.util.RequestParams;
import si.opkp.util.Util;

@RestController
@RequestMapping("v1/graph")
public class GraphController {

	private static GraphController instance;

	public static GraphController getInstance() {
		return instance;
	}

	@Autowired
	private Database db;

	@PostConstruct
	private void init() {
		instance = this;
	}

	@RequestMapping(value = "/{model}", method = RequestMethod.GET)
	public ResponseEntity<Pojo> get(@PathVariable("model") String model,
											  @ModelAttribute RequestParams params) {
		return perform(model, params);
	}

	public ResponseEntity<Pojo> perform(String model, RequestParams params) {
		try {
			String[] path = model.split(",");

			Optional<String> err = Validator.validate(path, params.getColumns(), params.getSort());

			if (err.isPresent()) {
				return Util.responseError(err.get(), HttpStatus.BAD_REQUEST);
			}

			DataGraph dg = DataGraph.getInstance();
			SelectBuilder selectBuilder = QueryFactory.select()
																	.expr(params.getColumns())
																	.from(path[0]);
			SelectBuilder countBuilder = QueryFactory.select()
																  .expr(new RequestColumn("COUNT(*) as count"))
																  .from(path[0]);

			for (int i = 1; i < path.length; i++) {
				String a = path[i - 1];
				String b = path[i];

				Optional<String> edge = dg.getEdge(a, b);

				if (edge.isPresent()) {
					selectBuilder.join(b, edge.get());
					countBuilder.join(b, edge.get());
				} else {
					return Util.responseError(String.format("cannot join '%s' and '%s'", a, b), HttpStatus.BAD_REQUEST);
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

			List<Pojo> objects = db.queryObjects(selectBuilder.build());
			long total = db.queryObject(countBuilder.build())
								.getLong("count");

			return Util.createResult(objects, total);
		} catch (Exception e) {
			e.printStackTrace();

			return Util.responseError(HttpStatus.BAD_REQUEST);
		}
	}

}
