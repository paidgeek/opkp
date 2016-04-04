package si.opkp;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import si.opkp.model.MySQLDatabase;
import si.opkp.model.NodeSuccessResult;
import si.opkp.util.Pojo;

@Component
@Primary
class DatabaseMock extends MySQLDatabase {

	@Override
	public NodeSuccessResult callFunction(String function, Object... args) {
		List<Pojo> result = new ArrayList<>();

		if (function.equals("ping")) {
			result.add(new Pojo.Builder()
					.property("ping", "pong")
					.build());
		}

		return new NodeSuccessResult(result, 1);
	}

}