package si.opkp.controller;

import com.moybl.restql.RestQL;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import si.opkp.util.Pojo;
import si.opkp.util.SQLUpdateBuilder;

@RestController
@RequestMapping("/v1/update")
@CrossOrigin
public class UpdateController {

	@RequestMapping(value = "/{model}", method = RequestMethod.PUT)
	public ResponseEntity<Pojo> update(@PathVariable("model") String model,
	                                   @RequestParam("q") String query,
	                                   @RequestBody Pojo body) {
		SQLUpdateBuilder updateBuilder = new SQLUpdateBuilder(model);

		body.getProperties().entrySet()
				.forEach(prop -> updateBuilder.set(prop.getKey(), prop.getValue()));

		updateBuilder.where(RestQL.parseToSQL(query));

		System.out.println(updateBuilder.build());

		return ResponseEntity.ok(body);
	}

}
