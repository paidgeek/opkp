package si.opkp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import si.opkp.controller.middlewares.GraphValidation;
import si.opkp.model.Database;
import si.opkp.query.ConditionBuilder;
import si.opkp.query.QueryFactory;
import si.opkp.query.SelectBuilder;
import si.opkp.util.DirectedGraph;
import si.opkp.util.Pojo;
import si.opkp.util.RequestParams;
import si.opkp.util.Util;

@Component("graph")
@Use(classes = {GraphValidation.class})
class GraphController implements Controller {

	@Autowired
	private Database database;

	@Override
	public ResponseEntity<Pojo> get(String[] model, RequestParams params) {
		SelectBuilder selectBuilder = QueryFactory.select()
																.fields(params.getFields())
																.from(model[0]);
		SelectBuilder countBuilder = QueryFactory.select()
															  .from(model[0]);

		for (int i = 1; i < model.length; i++) {
			String a = model[i - 1];
			String b = model[i];

			Optional<DirectedGraph<String, ConditionBuilder>.Edge> edge = database.getDataGraph()
																										 .getEdge(a, b);
			selectBuilder.join(b, edge.get()
											  .getValue());
			countBuilder.join(b, edge.get()
											 .getValue());
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
