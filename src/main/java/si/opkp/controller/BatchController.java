package si.opkp.controller;

import com.moybl.restql.Engine;
import com.moybl.restql.ast.AstNode;
import com.moybl.restql.ast.Literal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	@Autowired
	private SearchController searchController;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> perform(@RequestBody Batch batch) {
		try {
			Pojo result = new Pojo();
			Map<String, Engine> states = new HashMap<>();
			List<Command> commands = batch.getCommands();

			OUTER_FOR:
			for (Command command : commands) {
				for (Dependency dependency : command.getDependencies()) {
					Engine state = states.get(dependency.getCommand());

					if (!state.evaluate(dependency.getCondition())
								 .equals(Literal.trueLiteral())) {
						result.setProperty(command.getName(),
								String.format("command was terminated, because condition '%s' failed for command '%s'",
										dependency.getCondition(),
										dependency.getCommand()));

						continue OUTER_FOR;
					}
				}

				Engine state = new Engine();
				String controller = command.getController();
				List<AstNode> arguments = command.getArguments();

				states.put(command.getName(), state);

				ResponseEntity<?> response;

				switch (controller) {
					case "path":
						response = pathController.get(arguments, command.getParams());
						break;
					case "search":
						response = searchController.get(arguments, command.getParams());
						break;
					default:
						state.setVariable("status", (double) HttpStatus.BAD_REQUEST.value());
						result.setProperty(command.getName(), Util.createError("invalid controller"));

						continue;
				}

				state.setVariable("status", response.getStatusCode()
																.value());
				result.setProperty(command.getName(), response.getBody());
			}

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			e.printStackTrace();

			return Util.responseError(HttpStatus.BAD_REQUEST);
		}
	}

}
