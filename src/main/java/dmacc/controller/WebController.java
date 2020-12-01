/*************************************************
 * author: 	
 * course:	CIS 171 Java II
 * date:	
 * project:	ToolLibrary - group project
 * file:	WebController.java
 * summary:	
 ************************************************/

package dmacc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dmacc.beans.Member;
import dmacc.beans.Tool;
import dmacc.repository.MemberRepo;
import dmacc.repository.RentalRepo;
import dmacc.repository.ToolRepo;

@Controller
public class WebController {
	// Ben - added repository instance for Member, Tool, and Rental entities 
	@Autowired
	MemberRepo memberRepo;
	@Autowired
	RentalRepo rentalRepo;
	@Autowired
	ToolRepo toolRepo;
	
	@GetMapping("viewAllTools")
	public String viewAllTools(Model model) {		
		model.addAttribute("tool", toolRepo.findAll());
		return "viewAllTools";
	}
	/**
	 * process search form from viewAllTools UPDATED: 12/01/2020 by Ben Miner
	 * @param keyword
	 * @param model
	 * @return
	 */
	@PostMapping("/search")
	public String viewAllTools(@Param("keyword") String keyword,Model model) {
		model.addAttribute("tool", toolRepo.keywordSearch(keyword));
		return "viewAllTools";
	}
	/**
	 * process login information UPDATED: 11/30/2020 by Ben Miner
	 * @param username
	 * @param password
	 * @param model
	 * @return
	 */
	@PostMapping("/login")
	public String login(@RequestParam(name="username") String username, @RequestParam(name="password") String password, Model model) {
		if(memberRepo.memberExist(username, password)) {
			Member m = memberRepo.findByusernameAndPassword(username, password);
			model.addAttribute("memberInfo", m);
			return "login";
		}
		else {
			return "signUp";
		}
	}
	
	@PostMapping("/borrow/{id}")
	public String reviseTool(Tool t, Model model) {
		toolRepo.save(t);
		return viewAllTools(model);
	}
}
