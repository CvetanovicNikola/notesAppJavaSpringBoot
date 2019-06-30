package com.notes.notesApp.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

 
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{		
	@Autowired
	private UserDetailsService userDetailsService;
	
	
	@Bean
	public AuthenticationProvider authProvider()
	{
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance()); //new BCryptPasswordEncoder()
		return provider;
	}

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
         .csrf().disable()
         .authorizeRequests()
         .antMatchers("/javax.faces.resource/**").permitAll()
         .antMatchers("/js/**").permitAll()
         .antMatchers("/css/**").permitAll()
         .antMatchers("/login").permitAll()
         .antMatchers("/").permitAll()
         .antMatchers("/createUser.xhtml").permitAll()
         .antMatchers("/index.xhtml").permitAll()
         .antMatchers("/userDetail.xhtml").permitAll()
         .anyRequest().authenticated()
         .and()
         .formLogin()
         .loginPage("/login").permitAll()
         .defaultSuccessUrl("/allNotes.xhtml", true)
         //.failureUrl("/login.xhtml?error=true")
         .and()
         .logout().invalidateHttpSession(true)
         .logoutSuccessUrl("/login.xhtml").permitAll()
         .clearAuthentication(true)
         .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
        
    }

 }