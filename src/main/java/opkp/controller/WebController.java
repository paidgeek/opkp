package opkp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("x", 42);

		return "index";
	}

}
