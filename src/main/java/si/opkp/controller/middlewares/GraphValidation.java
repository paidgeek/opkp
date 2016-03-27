package si.opkp.controller.middlewares;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

import si.opkp.controller.Middleware;
import si.opkp.model.Database;
import si.opkp.query.ConditionBuilder;
import si.opkp.util.DirectedGraph;
import si.opkp.util.Pojo;
import si.opkp.util.RequestParams;
import si.opkp.util.Util;

@Component
public class GraphValidation implements Middleware {

	@Autowired
	private Database database;

	@Override
	public ResponseEntity<Pojo> get(String[] model, RequestParams params) {
		for (int i = 0; i < model.length; i++) {
			String a = model[i];

			if (!database.getDataGraph()
							 .getNodes()
							 .contains(a)) {
				return Util.responseError("invalid node '" + a + "'", HttpStatus.BAD_REQUEST);
			}

			if (i < model.length - 1) {
				String b = model[i + 1];
				Optional<DirectedGraph<String, ConditionBuilder>.Edge> edge = database.getDataGraph()
																											 .getEdge(a, b);
				if (!edge.isPresent()) {
					return Util.responseError(String.format("can not join '%s' and '%s'", a, b), HttpStatus.BAD_REQUEST);
				}
			}
		}

		return null;
	}

}
