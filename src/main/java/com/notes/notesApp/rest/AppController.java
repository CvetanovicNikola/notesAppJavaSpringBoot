package com.notes.notesApp.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppController {
	
	@Autowired
	UserController userController;
	
	@RequestMapping("/")
	public String home() {
		return "index.xhtml";
	}
	
	@RequestMapping("/login")
		public String login() {
		return "userLogin.xhtml";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
		return "loggedOut.xhtml";
	}
	@RequestMapping("/createUser")
	public String createUser() {
		return "createUSer.xhtml";
	}
	@RequestMapping("/admin")
	public String admin() {
		return "adminLogin.xhtml";
	}
}
