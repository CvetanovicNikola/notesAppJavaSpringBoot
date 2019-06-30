package com.notes.notesApp.JSF;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
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
	private User user ;
	private String message;
	

	
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
		return "index.xhtml?faces-redirect=true";
	}
	public List<User> getAllUsers(){
		return userController.getAllUsers();
	}


	
	
	
}
