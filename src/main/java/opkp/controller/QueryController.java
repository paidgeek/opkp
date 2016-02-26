package opkp.controller;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueryController {

	@RequestMapping(name = "execute", method = RequestMethod.GET)
	public Object execute(@RequestParam(value = "q") String query) {
		if (query != null && !query.isEmpty()) {
			Node rootNode = new RSQLParser().parse(query);
			SQLGenerator sqlGenerator = new SQLGenerator();

			rootNode.accept(sqlGenerator);

			return sqlGenerator.result();
		} else {
			return null;
		}
	}

}
