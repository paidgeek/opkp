package si.opkp.controller;

import com.moybl.restql.RestQLLexer;
import com.moybl.restql.RestQLParser;
import com.moybl.restql.generators.SQLGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import si.opkp.model.Database;
import si.opkp.model.QueryFactory;
import si.opkp.util.Pojo;
import si.opkp.util.RelationMap;
import si.opkp.util.SQLSelectBuilder;
import si.opkp.util.Util;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/v1/path")
public class PathController {

	@Autowired
	private Database db;

	@RequestMapping("/{nodes}")
	public ResponseEntity<Pojo> traverse(@PathVariable("nodes") String nodes,
	                                     @RequestParam(name = "fields", required = false, defaultValue = "*") String fields,
	                                     @RequestParam(name = "sort", required = false) String sort,
	                                     @RequestParam(name = "q", required = false) String query,
	                                     @RequestParam(name = "limit", required = false, defaultValue = "0,20") String limit) {
		String[] select = Util.parseFieldList(fields);
		String[] orderBy = Util.parseFieldList(sort);
		int[] boundary = Util.parseLimit(limit);

		// TODO uughh :/
		String[] path = nodes.split("><|<>|<|>");

		for (int i = 0; i < path.length; i++) {
			nodes = nodes.replaceAll(path[i], ",");
		}

		String[] joins = nodes.substring(1).split(",");

		String sql = QueryFactory.select(select, path, joins, query, orderBy, boundary);

		if (sql == null) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}

		Pojo result = new Pojo();
		Pojo meta = new Pojo();

		List<Pojo> objects = db.queryObjects(sql);
		meta.setProperty("count", objects.size());

		result.setProperty("meta", meta);
		result.setProperty("result", objects);

		return ResponseEntity.ok(result);
	}

}
