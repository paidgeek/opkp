package opkp.model;

import opkp.OPKPService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Food {

	private String id;

	private Food() {
	}

	public String getId() {
		return id;
	}

	public static Food fromResultSet(ResultSet resultSet) {
		Food food = new Food();

		try {
			food.id = resultSet.getString("ORIGFDCD");
		} catch (SQLException e) {
			OPKPService.getLogger().error(e.getMessage());
		}

		return food;
	}

}
