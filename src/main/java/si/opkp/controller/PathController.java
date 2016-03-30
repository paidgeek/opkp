package si.opkp.controller;

import com.moybl.restql.ast.AstNode;
import com.moybl.restql.ast.Identifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	public ResponseEntity<?> get(List<AstNode> arguments, RequestParams params) {
		List<String> argPath = arguments.stream()
												  .map(node -> ((Identifier) node).getName())
												  .collect(Collectors.toList());

		SelectBuilder select = QueryFactory.select(database);

		select.fields(params.getFields());
		select.from(arguments.get(0));

		List<String> nodes = new ArrayList<>();
		nodes.add(argPath.get(0));

		// joins
		for (int i = 1; i < arguments.size(); i++) {
			String other = argPath.get(i);

			Optional<List<String>> optPath = database.getDataGraph()
																  .findPath(argPath.get(i - 1), other);

			if (!optPath.isPresent()) {
				return Util.responseError(String.format("cannot join '%s' and '%s'",
						argPath.get(i - 1),
						other),
						HttpStatus.BAD_REQUEST);
			}

			List<String> path = optPath.get();

			for (int j = 1; j < path.size(); j++) {
				if (!nodes.contains(path.get(j))) {
					nodes.add(path.get(j));
				}
			}
		}

		for (int i = 1; i < nodes.size(); i++) {
			AstNode edge = database.getDataGraph()
										  .getEdge(nodes.get(i - 1), nodes.get(i))
										  .get();

			select.join(new Identifier(nodes.get(i)), edge);
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
	public ResponseEntity<?> post(List<AstNode> arguments, RequestParams params, Pojo body) {
		return Util.responseError(HttpStatus.BAD_REQUEST);
	}

}
