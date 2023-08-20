package com.enotes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.enotes.entity.Notes;
import com.enotes.entity.UserDtls;
import com.enotes.repository.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class UserService {
	
	

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private NotesRepo notesRepo;
	
	
	public UserDtls saveUser(UserDtls ud) {
		UserDtls user=userRepo.save(ud);
		return user;
	}
	public Notes saveNotes(Notes notes,String email) {
		UserDtls u=userRepo.findByEmail(email);
		notes.setUserDtls(u);
		System.out.println(notes);
		Notes n=notesRepo.save(notes);
		return n;
	}
	
	
	public void removeVerificationMessageFromSession() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            HttpSession session = request.getSession();
            session.removeAttribute("msg");
        } catch (RuntimeException ex) {
           
        }
    }
}
