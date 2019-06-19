package com.notes.notesApp.rest;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.notes.notesApp.exceptions.UserNotfoundException;
import com.notes.notesApp.model.User;
import com.notes.notesApp.repository.UserRepository;

@RestController
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@PostMapping("/users")
	public User createUser(@RequestBody @Valid User user) {
		
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
					u.setPassword(user.getPassword());
					u.setUsername(user.getUsername());
					u.setCreated(user.getCreated());
					return userRepository.save(u);
				}).orElseThrow(() -> new UserNotfoundException("No such user!"));
	}

}
