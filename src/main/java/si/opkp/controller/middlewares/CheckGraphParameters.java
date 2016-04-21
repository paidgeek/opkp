package si.opkp.controller.middlewares;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import si.opkp.controller.ControllerAdapter;
import si.opkp.controller.GraphController;
import si.opkp.controller.Middleware;
import si.opkp.model.Database;
import si.opkp.util.Pojo;
import si.opkp.util.RequestParams;
import si.opkp.util.Util;

@Component
public class CheckGraphParameters implements Middleware {

	@Autowired
	private Database database;

	@Override
	public ResponseEntity<?> get(ControllerAdapter controllerAdapter, String[] path, RequestParams params) {
		if (controllerAdapter instanceof GraphController) {
			if (path.length == 0) {
				return Util.responseError("no path specified", HttpStatus.BAD_REQUEST);
			} else if (path.length == 1) {
				String node = path[0];

				if (!database.getDataGraph().getNodes().contains(node)) {
					return Util.responseError("unknown node", HttpStatus.BAD_REQUEST);
				}
			}
		}

		return ResponseEntity.ok(null);
	}

	@Override
	public ResponseEntity<?> post(ControllerAdapter controllerAdapter, String[] path, Pojo body) {
		return ResponseEntity.ok(null);
	}

}
