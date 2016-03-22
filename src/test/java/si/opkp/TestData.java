package si.opkp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.io.IOException;

import javax.sql.DataSource;

@Configuration
public class TestData {

	@Bean
	public DataSource dataSource() throws IOException {

		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
														.addScript("schema.sql")
														.addScript("data.sql")
														.build();
	}

}
