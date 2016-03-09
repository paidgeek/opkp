package si.opkp.fitbit;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import si.opkp.controller.*;
import si.opkp.model.*;
import si.opkp.util.*;

@RestController
@RequestMapping("/v1/fitbit")
@CrossOrigin
public class FitbitController {

	@Autowired
	private Database db;

	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public ResponseEntity<Pojo> test() {
		Pojo body = new Pojo();

		body.setProperty("FITBIT_ID", "SEGPOSEG");

		CRUDController.getInstance().performCreate("fitbit_user", body);

		return ResponseEntity.ok(null);
	}

}
