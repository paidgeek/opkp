package si.opkp.controller;

import com.moybl.restql.ast.AstNode;
import com.moybl.restql.ast.Identifier;
import com.moybl.restql.factory.RestQLBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import si.opkp.model.Database;
import si.opkp.query.QueryFactory;
import si.opkp.query.SelectBuilder;
import si.opkp.util.Pojo;
import si.opkp.util.RequestParams;
import si.opkp.util.Util;

@Component
public class PathController implements Controller {

	@Autowired
	private Database database;

	@Override
	public ResponseEntity<Pojo> get(List<Identifier> arguments, RequestParams params) {
		SelectBuilder select = QueryFactory.select();
		RestQLBuilder b = new RestQLBuilder();

		select.fields(params.getFields());
		select.from(arguments.get(0));

		// joins
		String from = arguments.get(0)
									  .getName();

		for (int i = 1; i < arguments.size(); i++) {
			String other = arguments.get(i)
											.getName();

			Optional<List<String>> optPath = database.getDataGraph()
																  .findPath(from, other);

			if (!optPath.isPresent()) {
				return Util.responseError("cannot join '" + from + "' and '" + other + "'", HttpStatus.BAD_REQUEST);
			}

			List<String> path = optPath.get();

			for (int j = 1; j < path.size(); j++) {
				AstNode edge = database.getDataGraph()
											  .getEdge(path.get(j - 1), path.get(j))
											  .get();
				select.join(new Identifier(path.get(j)), edge);
			}
		}

		select.skip(params.getSkip());
		select.take(params.getTake());

		if (params.getGroup() != null) {
			select.group(params.getGroup());
		}

		if (params.getSort() != null) {
			select.sort(params.getSort());
		}

		try {
			List<Pojo> objects = database.queryObjects(select);

			return Util.createResult(objects, -1);
		} catch (Exception e) {
			e.printStackTrace();

			return Util.responseError(HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<Pojo> post(List<Identifier> arguments, RequestParams params, Pojo body) {
		return Util.responseError(HttpStatus.BAD_REQUEST);
	}

}
