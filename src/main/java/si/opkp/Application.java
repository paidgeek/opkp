package si.opkp;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.security.oauth2.client.*;
import org.springframework.context.*;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableOAuth2Sso
@ImportResource({"beans.xml"})
public class Application extends WebSecurityConfigurerAdapter {

	private static ApplicationContext context;

	public static ApplicationContext getContext() {
		return context;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.antMatcher("/**")
				.authorizeRequests()
				.antMatchers("/", "/login**", "/webjars/**")
				.permitAll()
				.anyRequest()
				.authenticated();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
