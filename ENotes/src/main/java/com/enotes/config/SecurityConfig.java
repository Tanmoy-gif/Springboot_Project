package com.enotes.config;

import org.hibernate.cache.internal.DisabledCaching;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	public CustomSuccessHandler successHandler;
	
	/*
	 * @Bean protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception { auth.authenticationProvider(daoAuthenticationProvider()); }
	 */
	 
	
	@Bean
	public UserDetailsService gerUserDetailsService() {
		
		return new UserDetailsServiceImpl();
		
	}
	
	@Bean
	public BCryptPasswordEncoder getPassword() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider dao=new DaoAuthenticationProvider();
		dao.setUserDetailsService(gerUserDetailsService());
		dao.setPasswordEncoder(getPassword());
		
		
		return dao;
	}
	
	
	//@SuppressWarnings("deprecation")
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
		
		/*
		 * http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN").
		 * antMatchers("/user/**").hasRole("USER")
		 * .antMatchers("/**").permitAll().and().formLogin().loginPage("/login").
		 * loginProcessingUrl("/login")
		 * .defaultSuccessUrl("/user/addNotes").and().csrf().disable();
		 
		
		
		
		http.csrf().disable().authorizeHttpRequests().requestMatchers("/user/**").hasRole("USER")
		.requestMatchers("/**").permitAll()
		.and().formLogin()
		.loginPage("/login").loginProcessingUrl("/login")
		 .defaultSuccessUrl("/addNotes",true);
		http.authenticationProvider(daoAuthenticationProvider());
		
		return http.build();*/
		
		
		http.csrf().disable()
		.authorizeHttpRequests().requestMatchers("/user/**").hasRole("USER")
		.requestMatchers("/admin/**").hasRole("ADMIN")
		.requestMatchers("/**").permitAll().and()
		.formLogin().loginPage("/login").loginProcessingUrl("/login")
		.successHandler(successHandler)
		.and().logout().permitAll();
		
		return http.build();
        
    }
	
	

}
