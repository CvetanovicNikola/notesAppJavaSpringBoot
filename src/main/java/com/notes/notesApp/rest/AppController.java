package com.notes.notesApp.rest;

import org.springframework.beans.factory.annotation.Autowired;
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
	public String logout() {
	return "logout.jsp";
	}
	@RequestMapping("/createUser")
	public String createUser() {
		return "createUSer.xhtml";
	}
}
