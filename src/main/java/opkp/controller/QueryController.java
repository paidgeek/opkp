package opkp.controller;

import opkp.OPKPService;
import opkp.model.Food;
import opkp.parser.SQLBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueryController {

	@RequestMapping(name = "select", method = RequestMethod.GET)
	public Object select(@RequestParam(value = "from") String table, @RequestParam(value = "where") String condition) {
		String query = new SQLBuilder()
				.select()
				.from(table)
				.where(condition)
				.build();

		return OPKPService.getDatabase().query(query, (rs, rowNum) -> Food.fromResultSet(rs));
	}

}
