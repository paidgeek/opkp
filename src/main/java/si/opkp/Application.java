package si.opkp;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.security.oauth2.client.*;
import org.springframework.boot.builder.*;
import org.springframework.context.*;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.web.csrf.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.*;
import org.springframework.web.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.security.*;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableOAuth2Sso
@RestController
@ImportResource({"beans.xml"})
public class Application extends WebSecurityConfigurerAdapter {

	private static ApplicationContext context;

	public static ApplicationContext getContext() {
		return context;
	}

	@RequestMapping("/user")
	public Principal user(Principal principal) {
		return principal;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.antMatcher("/**")
				.authorizeRequests()
				.antMatchers("/", "/login**", "/webjars/**")
				.permitAll()
				.anyRequest()
				.authenticated()
				.and().logout().logoutSuccessUrl("/").permitAll()
				.and().csrf().csrfTokenRepository(csrfTokenRepository())
				.and().addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
	}

	private Filter csrfHeaderFilter() {
		return new OncePerRequestFilter() {
			@Override
			protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
													  FilterChain filterChain) throws ServletException, IOException {
				CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
				if (csrf != null) {
					Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
					String token = csrf.getToken();
					if (cookie == null || token != null && !token.equals(cookie.getValue())) {
						cookie = new Cookie("XSRF-TOKEN", token);
						cookie.setPath("/");
						response.addCookie(cookie);
					}
				}
				filterChain.doFilter(request, response);
			}
		};
	}

	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;
	}

	public static void main(String[] args) {
		context = new SpringApplicationBuilder()
				.bannerMode(Banner.Mode.OFF)
				.sources(Application.class)
				.run(args);
	}

}
