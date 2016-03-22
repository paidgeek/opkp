package si.opkp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.sql.DataSource;

@Configuration
public class ApplicationConfiguration extends WebSecurityConfigurerAdapter {

	@Bean
	public DataSource dataSource() throws IOException {
		InputStream is = new ClassPathResource("credentials.yml").getInputStream();
		HashMap<String, String> credentials = (HashMap) new Yaml().loadAs(is, HashMap.class)
																					 .get("credentials");

		return new DriverManagerDataSource(credentials.get("url"),
				credentials.get("username"),
				credentials.get("password"));
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
			 .disable();
	}

}
