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
			return signUp(model);
		}
	}
	/**
	 * return to login.html
	 * UPDATE: by Ben 12/10/2020
	 * @param model
	 * @return login.html
	 */
	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("memberInfo", current);
		return "login";
	}
	@GetMapping("/borrow/{id}/{member}")
	public String reviseTool(@PathVariable("id")long tId, @PathVariable("member")long mId, Model model) {
		Tool t = toolRepo.findById(tId).orElse(null);
		Member m = memberRepo.findById(mId).orElse(null);
		if(m == null) {
			return signUp(model);
		} else {
			Rental rent = new Rental();
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
	@GetMapping({"viewMyTools" })
	public String viewMyTools(Model model) {
		// Update: 12/01/2020 by Ben Miner - change model attribute to current member
		model.addAttribute("memberInfo", current);
		model.addAttribute("rental", rentalRepo.findByMemberId(current));
		return "viewMyTools";
	}
	
	//Added by Chadwick for return feature on viewMytools
	/**
	 * returnTool : return feature on viewMyTools
	 * UPDATED: by Chadwick
	 * @param rId
	 * @param model
	 * @return to viewMyTools
	 */
	// UPDATE: 12/03/2020 by Ben edits to edit @param rId and add repo save calls
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
	
	//Added by Eli for new member sign up
	@PostMapping("/signUp")
	public String signUp(@RequestParam(name="first") String first, @RequestParam(name="last") String last, @RequestParam(name="address") String address, @RequestParam(name="phone") String phone, @RequestParam(name="username") String username, @RequestParam(name="password") String password, Model model) {
		Member m = new Member();
		m.setFirst(first);
		m.setLast(last);
		m.setAddress(address);
		m.setPhone(phone);
		m.setUsername(username);
		m.setPassword(password);
		memberRepo.save(m);
		current = memberRepo.findByusernameAndPassword(username, password);
		UserSignInLog u = new UserSignInLog();
		u.setMemberId(current);
		userSignInLogRepo.save(u);
		model.addAttribute("memberInfo", current);
		return "login";
	}
	@GetMapping("/edit")
	public String editMember(Model model) {
		model.addAttribute("memberInfo", current);
		return "signUp";
	}
	@PostMapping("/update/{id}")
	public String updateMember(@RequestParam(name="first") String first, @RequestParam(name="last") String last, @RequestParam(name="address") String address, @RequestParam(name="phone") String phone, @RequestParam(name="username") String username, @RequestParam(name="password") String password, Model model) {
		current.setFirst(first);
		current.setLast(last);
		current.setAddress(address);
		current.setPhone(phone);
		current.setUsername(username);
		current.setPassword(password);
		memberRepo.save(current);
		model.addAttribute("memberInfo", current);
		return "login";
	}
	@GetMapping("/signUp")
	public String signUp(Model model) {
		Member m = new Member();
		model.addAttribute("memberInfo", m);
		return "signUp";
	}
	
	@GetMapping("/logOut")
	public String logOut(Model model) {
		current = new Member();
		return "index";
	}
	
}
