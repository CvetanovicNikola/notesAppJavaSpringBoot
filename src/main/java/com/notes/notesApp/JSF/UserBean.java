package com.notes.notesApp.JSF;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import com.notes.notesApp.exceptions.UserNotfoundException;
import com.notes.notesApp.model.User;
import com.notes.notesApp.rest.UserController;
import lombok.Data;

@SessionScoped
@Data
@Named("user")
public class UserBean implements Serializable{
	
	@Autowired
	private UserController userController;
	
	
	private String name;
	private String lastname;
	private String username;
	private String password;
	private String email;
	private String errorMessage;
	private String userId;
	private User user;
	private String message;
	private String updateUsername;
	private String updatePassword;
	private String updateEmail;
	

	
	public User getUserById() {
		int id = Integer.valueOf(userId);
		return userController.getUserById(id);
	}
	
	public String createUser() {
		User u = new User();
		u.setUsername(username); 
		u.setPassword(password);
		u.setEmail(email);
		u.setFirstName(name);
		u.setLastName(lastname);
		
		try {
			userController.createUser(u);
			return "userDetail.xhtml?userId=" + u.getUser_id() + "&faces-redirect=true";
				
		}
		catch (Exception a) {
			errorMessage = a.getMessage();
			return "createUser.xhtml?faces-redirect=true";
		}
		
			
	}
	
	public String deleteUser(String id) {
		int userId = Integer.valueOf(id);
		userController.deleteUser(userId);
		return "admin.xhtml?faces-redirect=true";
	}
	public List<User> getAllUsers(){
		return userController.getAllUsers();
	}

	public String updateUser() {
		User user = new User();
		System.out.println(getLoggedUserId());
		user = userController.getUserById(getLoggedUserId());
		
		if(!updateUsername.isEmpty() && updateUsername != null) {user.setUsername(updateUsername);}
		if(!updatePassword.isEmpty() && updatePassword != null) {user.setPassword(updatePassword);}
		if(!updateEmail.isEmpty() && updateEmail != null) {user.setEmail(updateEmail);}
		userController.updateUser(user.getUser_id(), user);
		updateUsername = "";
		updatePassword = "";
		updateEmail = "";
		return "updateUser.xhtml?faces-redirect=true";
		
	}
	
	public long getLoggedUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username= authentication.getName();
		User user = userController.getAllUsers().stream()
				.filter(u -> u.getUsername().equals(username))
				.findFirst()
				.orElseThrow(() -> new UserNotfoundException("No such user!"));
		return user.getUser_id();
	}
	
}
