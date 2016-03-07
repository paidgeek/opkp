package si.opkp.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import si.opkp.batch.*;
import si.opkp.model.*;
import si.opkp.util.*;

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

			for (Command command : commands) {
				Optional<String> failedDependency = command.getDependencies()
						.stream()
						.filter(failed::contains)
						.findAny();

				if (failedDependency.isPresent()) {
					failed.add(command.getName());

					result.setProperty(command.getName(), Util.createError(
							String.format("command '%s' was terminated, because '%s' failed",
									command.getName(),
									failedDependency.get())));

					continue;
				}

				String controller = command.getController();
				String model = command.getModel();

				ResponseEntity<Pojo> response;

				switch (controller) {
					case "path":
						response = PathController.getInstance()
								.perform(model,
										command.getColumns(),
										command.getQuery(),
										command.getSort(),
										command.getLimit());
						break;
					case "search":
						response = SearchController.getInstance()
								.perform(model,
										command.getColumns(),
										command.getKeywords(),
										command.getLimit(),
										command.getLanguage());
						break;
					default:
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
