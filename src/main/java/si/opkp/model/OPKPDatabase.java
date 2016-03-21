package si.opkp.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import si.opkp.Application;
import si.opkp.util.Pojo;

@Repository
public class OPKPDatabase implements Database {

	@Autowired
	private ResourceLoader resourceLoader;

	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	public void init() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");

		try {
			InputStream is = resourceLoader.getResource("classpath:credentials.yml")
													 .getInputStream();
			HashMap<String, String> credentials = (HashMap) new Yaml().loadAs(is, HashMap.class)
																						 .get("credentials");

			dataSource.setUrl(credentials.get("url"));
			dataSource.setUsername(credentials.get("username"));
			dataSource.setPassword(credentials.get("password"));
		} catch (Exception e) {
			dataSource.setUrl("mysql://localhost:3306");
			dataSource.setUsername("root");
			dataSource.setPassword("1234");
		}

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
