package si.opkp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import si.opkp.model.Database;
import si.opkp.util.Pojo;
import si.opkp.util.Util;

import java.util.*;

@RestController
@RequestMapping("/v1/batch")
@CrossOrigin
public class BatchController {

	@Autowired
	private Database db;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Pojo> post(@RequestBody Pojo batch) {
		try {
			List<HashMap<String, Object>> commands = (ArrayList) batch.getProperty("commands");

			Pojo result = new Pojo();

			for (HashMap<String, Object> command : commands) {
				if (!(command.containsKey("name") && command.containsKey("controller") && command.containsKey("params"))) {
					return Util.responseError("command must have a 'name', 'controller' and 'params' fields", HttpStatus.BAD_REQUEST);
				}

				String controller = (String) command.get("controller");
				String model = (String) command.get("model");
				HashMap<String, Object> params = (HashMap) command.get("params");

				List<String> columns = (ArrayList) params.get("columns");
				List<Long> limit = (ArrayList) params.get("limit");

				if (columns == null) {
					columns = Arrays.asList("*");
				}

				if (limit != null && limit.isEmpty()) {
					return Util.responseError("empty limit array", HttpStatus.BAD_REQUEST);
				}

				if (columns.isEmpty()) {
					return Util.responseError("no columns selected", HttpStatus.BAD_REQUEST);
				}

				ResponseEntity<Pojo> response = null;

				if (controller.equals("path")) {
					String query = (String) params.get("q");
					List<String> sort = (ArrayList) params.get("sort");

					response = PathController.getInstance()
							.perform(model, columns, query, sort, limit);
				} else if (controller.equals("get")) {
					List<String> keywords = (ArrayList) params.get("keywords");

					response = SearchController.getInstance()
							.perform(model, columns, keywords, limit);
				}

				if (response.getStatusCode() != HttpStatus.OK) {
					result.setProperty((String) command.get("name"), response.getBody());

					break;
				}

				result.setProperty((String) command.get("name"), response.getBody());
			}

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			e.printStackTrace();

			return Util.responseError(HttpStatus.BAD_REQUEST);
		}
	}

}
