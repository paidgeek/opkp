package si.opkp.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import si.opkp.util.Pojo;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class OPKPDatabase implements Database {

   private JdbcTemplate jdbcTemplate;

   @Autowired
   public void setDataSource(DataSource dataSource) {
      jdbcTemplate = new JdbcTemplate(dataSource);
   }

   public List<Pojo> queryObjects(String sql) {
      List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
      List<Pojo> objects = new ArrayList<>();

      result.forEach(e -> objects.add(new Pojo(e)));

      return objects;
   }

   @Override
   public List<Pojo> queryObjects(String sql, Object... args) {
      List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, args);
      List<Pojo> objects = new ArrayList<>();

      result.forEach(e -> objects.add(new Pojo(e)));

      return objects;
   }

   @Override
   public Pojo queryObject(String sql) {
      List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);

      if (result.isEmpty()) {
         return null;
      }

      return new Pojo(result.get(0));
   }

   @Override
   public Pojo queryObject(String sql, Object... args) {
      List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, args);

      if (result.isEmpty()) {
         return null;
      }

      return new Pojo(result.get(0));
   }

   @Override
   public int update(String sql) {
      return jdbcTemplate.update(sql);
   }

   @Override
   public int update(String sql, Object... args) {
      return jdbcTemplate.update(sql, args);
   }

}
