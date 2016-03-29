package si.opkp.controller;

import com.moybl.restql.ast.AstNode;
import com.moybl.restql.ast.Identifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import si.opkp.model.Database;
import si.opkp.model.QueryResult;
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
	public ResponseEntity<?> get(List<Identifier> arguments, RequestParams params) {
		SelectBuilder select = QueryFactory.select();

		select.fields(params.getFields());
		select.from(arguments.get(0));

		// joins
		for (int i = 1; i < arguments.size(); i++) {
			String other = arguments.get(i)
											.getName();

			Optional<List<String>> optPath = database.getDataGraph()
																  .findPath(arguments.get(i - 1)
																							.getName(), other);

			if (!optPath.isPresent()) {
				return Util.responseError(String.format("cannot join '%s' and '%s'",
						arguments.get(i - 1)
									.getName(),
						other),
						HttpStatus.BAD_REQUEST);
			}

			List<String> path = optPath.get();

			for (int j = 1; j < path.size(); j++) {
				AstNode edge = database.getDataGraph()
											  .getEdge(path.get(j - 1), path.get(j))
											  .get();
				select.join(new Identifier(path.get(j)), edge);
			}
		}

		if (params.getWhere() != null) {
			select.where(params.getWhere());
		}

		select.skip(params.getSkip())
				.take(params.getTake())
				.group(params.getGroup())
				.sort(params.getSort());

		try {
			QueryResult qr = database.queryObjects(select);

			return new ResponseEntity<>(qr, HttpStatus.valueOf(qr.getStatus()));
		} catch (Exception e) {
			e.printStackTrace();

			return Util.responseError(HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<?> post(List<Identifier> arguments, RequestParams params, Pojo body) {
		return Util.responseError(HttpStatus.BAD_REQUEST);
	}

}
