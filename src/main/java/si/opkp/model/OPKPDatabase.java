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
	public Pojo queryObject(String sql) {
		return null;
	}

}
