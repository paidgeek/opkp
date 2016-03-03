package si.opkp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import si.opkp.Application;
import si.opkp.model.Database;
import si.opkp.model.QueryFactory;
import si.opkp.util.Pojo;
import si.opkp.util.RelationMap;
import si.opkp.util.SQLSelectBuilder;
import si.opkp.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/v1/batch")
public class BatchController {

	@Autowired
	private Database db;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> execute(@RequestBody Pojo batch) {
		try {
			List<HashMap<String, Object>> commands = (ArrayList) batch.getProperty("commands");

			Pojo result = new Pojo();

			for (HashMap<String, Object> command : commands) {
				if (!(command.containsKey("name") && command.containsKey("path") && command.containsKey("params"))) {
					return new ResponseEntity(HttpStatus.BAD_REQUEST);
				}

				String[] path = ((String) command.get("path")).split("/");
				HashMap<String, String> params = (HashMap) command.get("params");

				String controller = path[0];

				if (controller.equals("path")) {
					String fields = params.get("fields");
					String sort = params.get("sort");
					String limit = params.get("limit");
					String query = params.get("q");

					if (limit == null) {
						limit = "0,20";
					}

					String[] select = Util.parseFieldList(fields);
					String[] orderBy = Util.parseFieldList(sort);
					int[] boundary = Util.parseLimit(limit);

					// TODO uughh :/
					String joinedNodes = path[1];
					String[] nodes = joinedNodes.split("><|<>|<|>");

					for (int i = 0; i < nodes.length; i++) {
						joinedNodes = joinedNodes.replaceAll(nodes[i], ",");
					}

					String[] joins = joinedNodes.substring(1).split(",");

					String sql = QueryFactory.select(select, nodes, joins, query, orderBy, boundary);

					if (sql == null) {
						return new ResponseEntity(HttpStatus.BAD_REQUEST);
					}

					Pojo commandResult = new Pojo();
					Pojo meta = new Pojo();

					List<Pojo> objects = db.queryObjects(sql);
					meta.setProperty("count", objects.size());

					commandResult.setProperty("meta", meta);
					commandResult.setProperty("result", objects);

					result.setProperty((String) command.get("name"), commandResult);
				}
			}

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}

}
