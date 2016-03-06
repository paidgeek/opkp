package si.opkp.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import si.opkp.util.*;

@RestController
@RequestMapping("/v1/create")
@CrossOrigin
public class CreateController {

	@RequestMapping(value = "/{model}", method = RequestMethod.POST)
	public ResponseEntity<Pojo> create(@PathVariable("model") String model,
												  @RequestBody Pojo body) {
		SQLInsertBuilder insertBuilder = new SQLInsertBuilder(model);

		body.getProperties().entrySet()
				.forEach(prop -> insertBuilder.insert(prop.getKey(), prop.getValue()));

		return ResponseEntity.ok(body);
	}

}
