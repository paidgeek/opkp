package si.opkp.model;

import si.opkp.util.Pojo;

import javax.sql.DataSource;
import java.util.List;

public interface Database {

	void setDataSource(DataSource dataSource);

	List<Pojo> queryObjects(String sql);

	Pojo queryObject(String sql);

}
