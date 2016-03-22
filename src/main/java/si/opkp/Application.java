package si.opkp;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.security.oauth2.client.*;
import org.springframework.context.*;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.web.csrf.*;
import org.springframework.web.filter.*;
import org.springframework.web.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;

@EnableAutoConfiguration
@ComponentScan
@PropertySources({
		@PropertySource("classpath:security.properties")
})
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
