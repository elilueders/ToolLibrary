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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

import dmacc.beans.Member;
import dmacc.beans.Rental;
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
	// global Member instance
	Member current = new Member();
	Rental rent = new Rental();
	
	@GetMapping("viewAllTools")
	public String viewAllTools(Model model) {		
		model.addAttribute("tool", toolRepo.findAll());
		model.addAttribute("memberInfo", current);
		//model.addAttribute("userSignInLog", userSignInLogRepo.findFirstByOrderByCurrentTimeStampDesc());
		return "viewAllTools";
	}
	/**
	 * "search" process search form from viewAllTools 
	 * UPDATED: 12/01/2020 by Ben Miner
	 * @param keyword
	 * @param model
	 * @return to viewAllTools
	 */
	@PostMapping("/search")
	public String viewAllTools(@Param("keyword") String keyword, Model model) {
		model.addAttribute("tool", toolRepo.keywordSearch(keyword));
		model.addAttribute("memberInfo", current);
		return "viewAllTools";
	}
	/**
	 * "login" process login information 
	 * UPDATED: 11/30/2020 by Ben Miner
	 * @param username
	 * @param password
	 * @param model
	 * @return to login or signUp
	 */
	@PostMapping("/login")
	public String login(@RequestParam(name="username") String username, @RequestParam(name="password") String password, Model model) {
		if(memberRepo.memberExist(username, password)) {
			current = memberRepo.findByusernameAndPassword(username, password);
			UserSignInLog u = new UserSignInLog();
			u.setMemberId(current);
			userSignInLogRepo.save(u);
			model.addAttribute("memberInfo", current);
			return "login";
		}
		else {
			return "signUp";
		}
	}
	
	@GetMapping("/borrow/{id}/{member}")
	public String reviseTool(@PathVariable("id")long tId, @PathVariable("member")long mId, Model model) {
		Tool t = toolRepo.findById(tId).orElse(null);
		Member m = memberRepo.findById(mId).orElse(null);
		if(m == null) {
			return "signUp";
		} else {
			rent = new Rental();
			rent.setToolId(t);
			rent.setMemberId(m);
			rent.setCheckedOut(new Date());
			t.setAvailable(false);
			rentalRepo.save(rent);
			toolRepo.save(t);
			return viewAllTools(model);
		}
	}
	
	// Brogan - add methods for view my tools page
	/**
	 * "viewMyTools" view tools member has checked out
	 * UPDATED: 11/30/2020 by Brogan
	 * @param model
	 * @return viewMyTools.html
	 */
	@GetMapping({"/viewMyTools" })
	public String viewMyTools(Model model) {	
		/*
		UserSignInLog newestTimeStamp;
		Member m;
		newestTimeStamp = userSignInLogRepo.findFirstByOrderByCurrentTimeStampDesc();
		m = newestTimeStamp.getMemberId();
		*/
		// Update: = 12/01/2020 by Ben Miner - change model attribute to current member
		model.addAttribute("memberInfo", current);
		model.addAttribute("rental", rentalRepo.findByMemberId(current));
		return "viewMyTools";
	}
	
	//Added by Chadwick for return feature on viewMytools
	@GetMapping("/return/{id}")
	public String returnTool(@PathVariable("id")long rId, Model model) {
		Rental r = rentalRepo.findById(rId).orElse(null);
		Tool t = toolRepo.findById(r.getToolId().getToolId()).orElse(null);
		t.setAvailable(true);
		r.setCheckedIN(new Date());
		toolRepo.save(t);
		rentalRepo.save(r);
		return viewMyTools(model);
	}
	
	
}
