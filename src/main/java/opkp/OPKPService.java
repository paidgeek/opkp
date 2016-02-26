package opkp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class OPKPService implements CommandLineRunner {

	private static OPKPService instance;
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static JdbcOperations getDatabase() {
		return instance.jdbcTemplate;
	}

	public static Logger getLogger() {
		return log;
	}

	@Autowired
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	@Override
	public void run(String... args) throws Exception {
		instance = this;

		jdbcTemplate = new JdbcTemplate(dataSource);
	}

}
