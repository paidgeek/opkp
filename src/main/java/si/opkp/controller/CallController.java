package si.opkp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import si.opkp.model.Database;
import si.opkp.util.Pojo;
import si.opkp.util.RequestParams;
import si.opkp.util.Util;

@Component("call")
class CallController implements Controller {

	@Autowired
	private Database database;

	@Override
	public ResponseEntity<Pojo> get(String[] model, RequestParams params) {
		Object[] args = Arrays.copyOfRange(model, 1, model.length);
		String function = model[0];

		List<Pojo> result = database.callFunction(function, model);

		return Util.createResult(result, result.size());
	}

}
