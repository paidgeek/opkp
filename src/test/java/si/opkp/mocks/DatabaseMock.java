package si.opkp.mocks;

import si.opkp.model.*;
import si.opkp.util.*;

import javax.sql.*;

import java.util.*;

public class DatabaseMock implements Database {

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

	@Override
	public int update(String sql) {
		return 0;
	}

	@Override
	public int update(String sql, Object... args) {
		return 0;
	}

}
