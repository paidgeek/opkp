package si.opkp.controller;

import com.moybl.restql.Engine;
import com.moybl.restql.RestQL;
import com.moybl.restql.ast.AstNode;
import com.moybl.restql.ast.Identifier;
import com.moybl.restql.ast.Literal;
import com.moybl.restql.ast.Sequence;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import si.opkp.batch.Batch;
import si.opkp.batch.Command;
import si.opkp.batch.Dependency;
import si.opkp.util.Pojo;
import si.opkp.util.Util;

@RestController
@CrossOrigin
@RequestMapping("v1/batch")
public class BatchController {

	@Autowired
	private Router router;

	/*
	@Override
	public ResponseEntity<?> post(String[] path, Pojo body) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Batch batch = mapper.readValue(mapper.writeValueAsString(body), Batch.class);

			return perform(batch);
		} catch (Exception e) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}
	*/

	@RequestMapping(method = RequestMethod.POST)
	private ResponseEntity<?> perform(@RequestBody Batch batch) {
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
				List<String> path = command.getPath();

				states.put(command.getName(), state);

				ResponseEntity<?> response = router.get(controller, path.toArray(new String[path.size()]), command.getParams());
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
