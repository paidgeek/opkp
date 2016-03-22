package si.opkp.model;

import java.util.List;

import si.opkp.util.Pojo;

public interface Database {

	List<Pojo> queryObjects(String sql);

	List<Pojo> queryObjects(String sql, Object... args);

	Pojo queryObject(String sql);

	Pojo queryObject(String sql, Object... args);

	long update(String sql);

	long update(String sql, Object... args);

}
