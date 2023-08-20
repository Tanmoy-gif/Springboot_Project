package com.enotes.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.enotes.entity.Notes;
import com.enotes.entity.UserDtls;
import com.enotes.repository.NotesRepo;
import com.enotes.repository.UserRepo;
import com.enotes.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(value="/user")
public class UserController {


	@Autowired
	private UserRepo userRepo;

	@Autowired
	private UserService userService;

	@Autowired
	private NotesRepo notesRepo;

	@ModelAttribute
	public void addCommonData(Principal p,Model m) {
		String email=p.getName();
		UserDtls user=userRepo.findByEmail(email);
		m.addAttribute("user",user);
	}

	//user/addNotes
	@GetMapping("/addNotes") 
	public String home() { 
		return "user/add_notes"; 
	}

	@GetMapping("/viewProfile") 
	public String viewProfile() { 
		return "user/view_profile"; 
	}

	@GetMapping("/editNotes/{id}") 
	public String editNotes(@PathVariable int id,Model m) { 
		Optional<Notes> n=   notesRepo.findById(id);
		if(n!=null) {
			Notes notes=n.get();
			m.addAttribute("notes",notes);
		}
		return "user/edit_notes"; 
	}

	@GetMapping("/deleteNotes/{id}") 
	public String deleteNotes(@PathVariable int id,RedirectAttributes redirAttrs) { 
		Optional<Notes> n=   notesRepo.findById(id);
		if(n!=null) {
			
			notesRepo.deleteById(id);
			redirAttrs.addFlashAttribute("msg","Notes deleted Successfully"); 
		}
		
		
		 else { 
			redirAttrs.addFlashAttribute("msg","Something wrong on server"); 
			}
		return  "redirect:/user/viewNotes/0";
	}

	@GetMapping("/viewNotes/{page}") 
	public String viewNotes(@PathVariable int page,Model m,Principal p) { 
		String email=p.getName();
		UserDtls user=userRepo.findByEmail(email);
		Pageable pageable=PageRequest.of(page, 5,Sort.by("id").descending());
		Page<Notes> notes=notesRepo.findNotesByUser(user.getId(), pageable);
		m.addAttribute("pageNo",page);
		m.addAttribute("totalPage",notes.getTotalPages());
		m.addAttribute("Notes",notes);
		m.addAttribute("totalElement",notes.getTotalElements());

		return "user/view_notes"; 
	}

	@PostMapping("/updateNotes")
	public String updateNotes(@ModelAttribute Notes notes,RedirectAttributes redirAttrs,Principal p,HttpSession session) {

		String email=p.getName();
		Notes n=null;
		if(!(notes.getTitle().isEmpty()) && !(notes.getContent().isBlank())) {

			n=userService.saveNotes(notes,email);
		}   



		if(n!=null) { 
			redirAttrs.addFlashAttribute("msg","Notes Saved Successfully"); 
		} else { 
			redirAttrs.addFlashAttribute("msg","Something wrong on server"); 
			}

		System.out.println(n);
		return "redirect:/user/viewNotes/0";

	}
	
	@PostMapping("/updateUser")
	public String updateUser(@ModelAttribute UserDtls user,RedirectAttributes redirAttrs,Principal p,HttpSession session) {

		String email=p.getName();
		UserDtls u=userRepo.findByEmail(email);
		
		
		if(u!=null) {
			user.setPassword(u.getPassword());
			user.setRole("ROLE_USER");
			UserDtls ud=userService.saveUser(user); 
			
			redirAttrs.addFlashAttribute("msg", "User Updated Successful..");
		}
		else {
			redirAttrs.addFlashAttribute("msg", "Something wrong on server");
		}

		System.out.println(u);
		return "redirect:/user/viewProfile";

	}


	@PostMapping("/saveNotes")
	public String saveNotes(@ModelAttribute Notes notes,RedirectAttributes redirAttrs,Principal p,HttpSession session) {

		String email=p.getName();
		Notes n=null;
		if(!(notes.getTitle().isEmpty()) && !(notes.getContent().isBlank())) {

			n=userService.saveNotes(notes,email);
		}    

		if(n!=null) {
			redirAttrs.addFlashAttribute("msg", "Notes Saved Successfully");
		}
		else {
			redirAttrs.addFlashAttribute("msg", "Something wrong on server");
		}
		return "redirect:/user/addNotes";

	}




}
