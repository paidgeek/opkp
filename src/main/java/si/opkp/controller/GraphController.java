package si.opkp.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import si.opkp.model.*;
import si.opkp.util.RestDto;
import si.opkp.util.*;

import javax.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("v1/graph")
public class GraphController {

	private static GraphController instance;
	@Autowired
	private Database db;

	public static GraphController getInstance() {
		return instance;
	}

	@PostConstruct
	private void init() {
		instance = this;
	}

	@RequestMapping(value = "/{model}", method = RequestMethod.GET)
	public ResponseEntity<Pojo> get(@PathVariable("model") String model,
											  @ModelAttribute RestDto params) {
		return perform(model, params);
	}

	public ResponseEntity<Pojo> perform(String model, RestDto params) {
		try {
			List<String> path = Arrays.asList(model.split(","));

			Optional<String> err = Validator.validate(path, params.getColumns(), params.getSort());

			if (err.isPresent()) {
				return Util.responseError(err.get(), HttpStatus.BAD_REQUEST);
			}

			DataGraph dg = DataGraph.getInstance();
			SQLSelectBuilder selectBuilder = new SQLSelectBuilder(params.getColumns())
					.from(path.get(0));
			SQLSelectBuilder countBuilder = new SQLSelectBuilder("COUNT(*) as count")
					.from(path.get(0));

			for (int i = 1; i < path.size(); i++) {
				String a = path.get(i - 1);
				String b = path.get(i);

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

			if (params.getSort() != null) {
				selectBuilder.orderByPrefixedColumns(params.getSort());
			}

			selectBuilder.limit(params.getLimit());

			List<Pojo> objects = db.queryObjects(selectBuilder.build());
			long total = db.queryObject(countBuilder.build()).getLong("count");

			Pojo result = new Pojo();
			Pojo meta = new Pojo();

			meta.setProperty("count", objects.size());
			meta.setProperty("total", total);

			result.setProperty("meta", meta);
			result.setProperty("result", objects);

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			e.printStackTrace();

			return Util.responseError(HttpStatus.BAD_REQUEST);
		}
	}

}
