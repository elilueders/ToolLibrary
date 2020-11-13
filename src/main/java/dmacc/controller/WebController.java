/*************************************************
 * author: 	
 * course:	CIS 171 Java II
 * date:	
 * project:	ToolLibrary - group project
 * file:	WebController.java
 * summary:	
 ************************************************/

package dmacc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebController {
	@GetMapping({ "/", "viewAllTools" })
	public String viewAllTools(Model model) {		
		model.addAttribute("tool", repo.findAll());
		return "viewAllTools";
	}
	
	@PostMapping("/borrow/{id}")
	public String reviseTool(Tool t, Model model) {
		repo.save(t);
		return viewAllTools(model);
	}
}
