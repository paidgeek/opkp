package si.opkp.controller;

import org.springframework.http.*;
import org.springframework.security.oauth2.provider.*;
import org.springframework.web.bind.annotation.*;
import si.opkp.util.*;

import java.security.*;
import java.util.*;

@RestController
@RequestMapping("v1/me")
public class MeController {

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Pojo> me(Principal principal) {
		if (principal instanceof OAuth2Authentication) {
			OAuth2Authentication auth = (OAuth2Authentication) principal;

			return ResponseEntity.ok(new Pojo((Map) auth.getPrincipal()));
		} else {
			return Util.responseError(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
