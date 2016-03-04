package si.opkp.model;

import si.opkp.util.Pojo;

import javax.sql.DataSource;
import java.util.List;

public interface Database {

   void setDataSource(DataSource dataSource);

   List<Pojo> queryObjects(String sql);

   List<Pojo> queryObjects(String sql, Object... args);

   Pojo queryObject(String sql);

   Pojo queryObject(String sql, Object... args);

   int update(String sql);

   int update(String sql, Object... args);

}
