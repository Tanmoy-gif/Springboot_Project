package com.enotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enotes.entity.UserDtls;


@Repository
public interface UserRepo extends JpaRepository<UserDtls, Integer>{
	
	public UserDtls findByEmail(String email);
	
}


