package si.opkp.controller;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebController {

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("/dashboard")
	public String dashboard() {
		return "dashboard";
	}

}
