package com.notes.notesApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notes.notesApp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByUsername(String username);
}
