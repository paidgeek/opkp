package si.opkp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import si.opkp.batch.Batch;
import si.opkp.batch.Command;
import si.opkp.model.Database;
import si.opkp.util.Pojo;
import si.opkp.util.Util;
import si.opkp.util.Validator;

import java.util.*;

@RestController
@RequestMapping("/v1/batch")
@CrossOrigin
public class BatchController {

	@Autowired
	private Database db;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Pojo> post(@RequestBody Batch batch) {
		String errorMessage = Validator.validate(batch);

		if (errorMessage != null) {
			return Util.responseError(errorMessage, HttpStatus.BAD_REQUEST);
		}

		try {
			Pojo result = new Pojo();
			List<Command> commands = batch.sortedCommands();
			Set<String> failed = new HashSet<>();

			for (int i = 0; i < commands.size(); i++) {
				Command command = commands.get(i);
				Optional<String> failedDependency = command.getDependencies().stream().filter(failed::contains).findAny();

				if (failedDependency.isPresent()) {
					failed.add(command.getName());

					String message = "command '" + command.getName() + "' terminated, because '" + failedDependency.get() + "' failed";
					result.setProperty(command.getName(), Util.createError(message));

					continue;
				}

				String controller = command.getController();
				String model = command.getModel();

				List<String> columns = (ArrayList) command.getParam("columns");
				List<Long> limit = (ArrayList) command.getParam("limit");

				if (columns == null) {
					columns = Arrays.asList("*");
				}

				ResponseEntity<Pojo> response = null;

				if (controller.equals("path")) {
					String query = (String) command.getParam("q");
					List<String> sort = (ArrayList) command.getParam("sort");

					response = PathController.getInstance()
							.perform(model, columns, query, sort, limit);
				} else if (controller.equals("get")) {
					List<String> keywords = (ArrayList) command.getParam("keywords");

					response = SearchController.getInstance()
							.perform(model, columns, keywords, limit);
				} else {
					failed.add(command.getName());
					result.setProperty(command.getName(), Util.createError("invalid controller"));

					continue;
				}

				if (response.getStatusCode() != HttpStatus.OK) {
					failed.add(command.getName());
					result.setProperty(command.getName(), response.getBody());

					continue;
				}

				result.setProperty(command.getName(), response.getBody());
			}

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			e.printStackTrace();

			return Util.responseError(HttpStatus.BAD_REQUEST);
		}
	}

}
