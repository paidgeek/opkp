package si.opkp.controller;

import com.moybl.restql.ast.Identifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import si.opkp.batch.Batch;
import si.opkp.batch.Command;
import si.opkp.batch.Dependency;
import si.opkp.util.Pojo;
import si.opkp.util.Util;

@RestController
@RequestMapping("v1/batch")
@CrossOrigin
public class BatchController {

	@Autowired
	private PathController pathController;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Pojo> perform(@RequestBody Batch batch) {
		try {
			Pojo result = new Pojo();
			List<Command> commands = batch.getCommands();
			Set<String> failed = new HashSet<>();

			for (Command command : commands) {
				Optional<Dependency> failedDependency = command.getDependencies()
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
				List<Identifier> arguments = command.getArguments();

				ResponseEntity<Pojo> response;

				switch (controller) {
					case "path":
						response = pathController.get(arguments, command.getParams());
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
