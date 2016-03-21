package si.opkp.controller;

import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import si.opkp.batch.*;
import si.opkp.model.Validator;
import si.opkp.util.*;

import java.util.*;

@RestController
@RequestMapping("v1/batch")
@CrossOrigin
public class BatchController {

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Pojo> post(@RequestBody Batch batch) {
		Optional<String> err = Validator.validate(batch);

		if (err.isPresent()) {
			return Util.responseError(err.get(), HttpStatus.BAD_REQUEST);
		}

		try {
			Pojo result = new Pojo();
			List<Command> commands = batch.getCommands();
			Set<String> failed = new HashSet<>();

			for (Command command : commands) {
				Optional<String> failedDependency = command.getDependencies()
						.stream()
						.filter(failed::contains)
						.findAny();

				if (failedDependency.isPresent()) {
					failed.add(command.getName());

					result.setProperty(command.getName(), Util.createError(
							String.format("command was terminated, because '%s' failed", failedDependency.get())));

					continue;
				}

				String controller = command.getController();
				String model = command.getModel();

				ResponseEntity<Pojo> response;

				switch (controller) {
					case "graph":
						response = GraphController.getInstance()
								.perform(model, command.getParams());
						break;
					case "search":
						response = SearchController.getInstance()
								.perform(model, command.getParams());
						break;
					case "crud":
						switch (command.getMethod()) {
							case GET:
								response = CRUDController.getInstance()
										.performRead(command.getModel(), command.getParams());
								break;
							case POST:
								response = CRUDController.getInstance()
										.performCreate(command.getModel(),
												command.getBody());
								break;
							case PUT:
								response = CRUDController.getInstance()
										.performUpdate(command.getModel(),
												command.getParams(),
												command.getBody());
								break;
							case DELETE:
								response = CRUDController.getInstance()
										.performDelete(command.getModel(),
												command.getParams());
								break;
							default:
								response = Util.responseError("invalid method", HttpStatus.BAD_REQUEST);
								break;
						}
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
