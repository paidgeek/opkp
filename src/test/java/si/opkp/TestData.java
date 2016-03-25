package si.opkp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.nio.file.Files;
import java.nio.file.Paths;

import javax.sql.DataSource;

@Configuration
public class TestData {

	@Bean
	public DataSource dataSource() throws Exception {
		SingleConnectionDataSource dataSource = new SingleConnectionDataSource();

		dataSource.setDriverClassName("org.sqlite.JDBC");
		dataSource.setUrl("jdbc:sqlite::memory:");

		dataSource.getConnection()
					 .createStatement()
					 .executeUpdate(new String(Files.readAllBytes(Paths.get(new ClassPathResource("test-data.sql").getURI()))));

		return dataSource;
	}

}
