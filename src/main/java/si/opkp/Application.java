package si.opkp;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.builder.*;
import org.springframework.context.*;
import org.springframework.context.annotation.*;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@ImportResource({"beans.xml"})
public class Application {

	private static ApplicationContext context;

	public static ApplicationContext getContext() {
		return context;
	}

	public static void main(String[] args) {
		context = new SpringApplicationBuilder()
				.bannerMode(Banner.Mode.OFF)
				.sources(Application.class)
				.run(args);
	}

}
