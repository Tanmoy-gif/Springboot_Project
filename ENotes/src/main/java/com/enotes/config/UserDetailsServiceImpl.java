package com.enotes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.enotes.entity.UserDtls;
import com.enotes.repository.UserRepo;

@Component
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepo repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		try {
			
			UserDtls u=repo.findByEmail(username);
			System.out.println(u);
			if(u==null) {
				throw new UsernameNotFoundException("No User");
			}
			else {
				return new CustomUserDetails(u);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}
	

}
