package com.notes.notesApp.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.notes.notesApp.model.User;
import com.notes.notesApp.repository.UserRepository;

@Service
public class MyUSerDetailsService implements UserDetailsService{

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findAll().stream()
				.filter(u -> u.getUsername().equals(username))
				.findFirst()
				.orElseThrow(() -> new UsernameNotFoundException("No such user!"));
		return new UserPrincipal(user);
	}

}
