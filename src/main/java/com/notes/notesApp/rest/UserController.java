package com.notes.notesApp.rest;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.notes.notesApp.exceptions.NoNumberInPasswordException;
import com.notes.notesApp.exceptions.NoUpperCaseInPasswordException;
import com.notes.notesApp.exceptions.UserNameTaken;
import com.notes.notesApp.exceptions.UserNotfoundException;
import com.notes.notesApp.model.User;
import com.notes.notesApp.repository.UserRepository;
import com.notes.notesApp.utils.PasswordChecker;

@RestController
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	//@Autowired
	//private BCryptPasswordEncoder encoder;
	
	@PostMapping("/users")
	public User createUser(@RequestBody @Valid User user) {
		if(!PasswordChecker.isThereANumber(user.getPassword())) {
			throw new NoNumberInPasswordException("You must use at least one number in a password!");
		}
		if (!PasswordChecker.isThereAnUpperCase(user.getPassword())) {
			throw new NoUpperCaseInPasswordException("You must use at least one uppercase in a password!");
		}
		//user.setPassword(encoder.encode(user.getPassword()));
		if(userRepository.findByUsername(user.getUsername()) != null) {
			throw new UserNameTaken("That username is already taken please choose another.");
		}
//		if(userRepository.findAll().stream()
//					.filter(u -> u.getUsername().equals(user.getUsername()))
//					.findAny()
//					.isPresent()) {
//			throw new UserNameTaken("That username is already taken please choose another.");
//		}
					
		user.setCreated(LocalDateTime.now());
		return userRepository.save(user);
	}
	
	@GetMapping("/users")
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	@GetMapping("/users/{user_id}")
	public User getUserById(@PathVariable long user_id) {
		return userRepository.findById(user_id)
				.orElseThrow(() -> new UserNotfoundException("No such user!"));
	}
	
	@DeleteMapping("/users/{user_id}")
	public void deleteUser(@PathVariable long user_id) {
		userRepository.delete(getUserById(user_id));
	}
	
	@PutMapping("users/{user_id}")
	public User updateUser(@PathVariable long user_id, @RequestBody @Valid User user) {
		return userRepository.findById(user_id)
				.map(u -> {
					u.setFirstName(user.getFirstName());
					u.setLastName(user.getLastName());
					if(!PasswordChecker.isThereANumber(user.getPassword())) {
						throw new NoNumberInPasswordException("You must use at least one number in a password!");
					}
					if (!PasswordChecker.isThereAnUpperCase(user.getPassword())) {
						throw new NoUpperCaseInPasswordException("You must use at least one uppercase in a password!");
					}
					u.setPassword(user.getPassword());
					u.setUsername(user.getUsername());
					u.setCreated(LocalDateTime.now());
					return userRepository.save(u);
				}).orElseThrow(() -> new UserNotfoundException("No such user!"));
	}
	
	@RequestMapping("login/")
	public String login(@RequestParam String username, @RequestParam String password, ModelMap modelMap) {
		User user = userRepository.findByUsername(username);
		if(user.getPassword().equals(password)) {
			return "findNotes";
		}else {
			modelMap.addAttribute("message", "Invalid username or password!");
		}
		return "login/login";
	}
	
	@RequestMapping("home/")
	public String home() {
		return "home";
	}

}
