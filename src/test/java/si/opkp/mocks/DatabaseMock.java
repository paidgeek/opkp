package si.opkp.mocks;

import si.opkp.model.Database;
import si.opkp.util.Pojo;

import javax.sql.DataSource;
import java.util.List;

public class DatabaseMock implements Database {

	@Override
	public void setDataSource(DataSource dataSource) {

	}

	@Override
	public List<Pojo> queryObjects(String sql) {
		return null;
	}

	@Override
	public List<Pojo> queryObjects(String sql, Object... args) {
		return null;
	}

	@Override
	public Pojo queryObject(String sql) {
		return null;
	}

	@Override
	public Pojo queryObject(String sql, Object... args) {
		return null;
	}
}
