package si.opkp.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import si.opkp.model.Database;
import si.opkp.util.Pojo;

@Component
@Primary
public class MockDatabase implements Database {

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

	public List<Pojo> queryObjects(String sql, Object... args) {
		List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, args);
		List<Pojo> objects = new ArrayList<>();

		result.forEach(e -> objects.add(new Pojo(e)));

		return objects;
	}

	public Pojo queryObject(String sql) {
		List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);

		if (result.isEmpty()) {
			return null;
		}

		return new Pojo(result.get(0));
	}

	public Pojo queryObject(String sql, Object... args) {
		List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, args);

		if (result.isEmpty()) {
			return null;
		}

		return new Pojo(result.get(0));
	}

	public long update(String sql) {
		return jdbcTemplate.update(sql);
	}

	public long update(String sql, Object... args) {
		return jdbcTemplate.update(sql, args);
	}

}
