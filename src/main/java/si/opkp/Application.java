package si.opkp;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@PropertySources({
		@PropertySource("classpath:security.properties")
})
public class Application {

	public static void main(String[] args) {
		new SpringApplicationBuilder()
				.sources(Application.class)
				.logStartupInfo(false)
				.bannerMode(Banner.Mode.OFF)
				.run(args);
	}

}
