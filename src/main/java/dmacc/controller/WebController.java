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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import dmacc.beans.Member;
import dmacc.beans.Tool;
import dmacc.beans.UserSignInLog;
import dmacc.repository.MemberRepo;
import dmacc.repository.RentalRepo;
import dmacc.repository.ToolRepo;
import dmacc.repository.UserSignInLogRepo;

@Controller
public class WebController {
	// Ben - added repository instance for Member, Tool, and Rental entities 
	@Autowired
	MemberRepo memberRepo;
	@Autowired
	RentalRepo rentalRepo;
	@Autowired
	ToolRepo toolRepo;
	@Autowired
	UserSignInLogRepo userSignInLogRepo;
	
	@GetMapping({"/", "viewAllTools" })
	public String viewAllTools(Model model) {		
		model.addAttribute("tool", toolRepo.findAll());
		model.addAttribute("userSignInLog", userSignInLogRepo.findFirstByOrderByCurrentTimeStampDesc());
		return "viewAllTools";
	}
	
	@PostMapping("/borrow/{id}")
	public String reviseTool(Tool t, Model model) {
		toolRepo.save(t);
		return viewAllTools(model);
	}
	
	// Brogan - add methods for view my tools page
	@GetMapping({"/viewMyTools" })
	public String viewMyTools(Model model) {	
		UserSignInLog newestTimeStamp;
		Member m;
		
		newestTimeStamp = userSignInLogRepo.findFirstByOrderByCurrentTimeStampDesc();
		m = newestTimeStamp.getMemberId();
		
		
		model.addAttribute("userSignInLog", userSignInLogRepo.findFirstByOrderByCurrentTimeStampDesc());
		model.addAttribute("rental", rentalRepo.findByMemberId(m));
		return "viewMyTools";
	}
	
}
