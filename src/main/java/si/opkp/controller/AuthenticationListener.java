package si.opkp.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.event.*;
import si.opkp.model.*;
import si.opkp.util.*;

import javax.servlet.http.*;
import java.util.*;

@Configuration
public class AuthenticationListener implements ApplicationListener<AbstractAuthenticationEvent> {

	private static final String FITBIT_USER_MODEL = "fitbit_user";

	@Autowired
	private HttpSession session;
	@Autowired
	private Database db;

	@Override
	public void onApplicationEvent(AbstractAuthenticationEvent event) {
		Pojo user = new Pojo((HashMap) event.getAuthentication().getPrincipal());

		CRUDController.getInstance().performCreate(FITBIT_USER_MODEL, user);
		CRUDController.getInstance().performCreate("test", new Pojo.Builder().setProperty("name", "AWPJOFOAFWOF").build());
	}

}
