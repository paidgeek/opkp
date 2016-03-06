package si.opkp.model;

import si.opkp.util.*;

import java.util.*;

public class DataDefinition {

	private static DataDefinition instance;

	private DataDefinition() {
		Map dd = (HashMap) Util.readFile("classpath:data-definition.json");
	}

	public static DataDefinition getInstance() {
		if (instance == null) {
			synchronized (DataDefinition.class) {
				if (instance == null) {
					instance = new DataDefinition();

					return instance;
				}
			}
		}

		return instance;
	}

}
