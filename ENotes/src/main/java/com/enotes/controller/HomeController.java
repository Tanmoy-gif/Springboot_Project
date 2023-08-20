package com.enotes.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.enotes.entity.UserDtls;
import com.enotes.repository.UserRepo;
import com.enotes.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	
	

	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder bp;
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}
	
	
	  //@GetMapping("/addNotes") public String gethome() { return "user/add_notes"; }
	  
	  

		
	 
	

	
	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute UserDtls user,Model m,RedirectAttributes redirAttrs) {
		System.out.println(user);
		
		user.setPassword(bp.encode(user.getPassword()));
		user.setRole("ROLE_USER");
		
		UserDtls ud=userService.saveUser(user);  
		if(ud!=null) {
			redirAttrs.addFlashAttribute("msg", "Registration Successful..");
		}
		else {
			redirAttrs.addFlashAttribute("msg", "Something wrong on server");
		}
		return "redirect:/signup";
		
	}
	
	
	
}
