package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService;
	
	@Bean
	public AuthenticationProvider authProvider() {
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		
		provider.setUserDetailsService(userDetailsService);
		
		provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance()); // NoOpPasswordEncoder : no use any encoder method 
					// setPasswordEncoder( new BCryptPasswordEncoder() ) : for Bcrypt encode password
		
		return provider;		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		/* http 
			.authorizeRequests()
				.antMatchers("/").permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
			.logout()
				.permitAll(); */
		
		  http	.csrf().disable() 				  		
		  		.authorizeRequests()	
		  		.antMatchers("/","/login").permitAll()  /* .antMatchers("/login").permitAll() */		  		
		  		.anyRequest().authenticated() 
		  		.and() 
		  		.formLogin()
		  		.loginPage("/login").permitAll() 
		  		.and() 
		  		.logout().invalidateHttpSession(true)
		  		.clearAuthentication(true) 
		  		.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) 
		  		.logoutSuccessUrl("/logout-success").permitAll();							
	}
		
}