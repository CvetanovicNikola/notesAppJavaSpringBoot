package com.notes.notesApp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@NoArgsConstructor
public class Admin {
	@Id
	@GeneratedValue
	private long adminId;
	@NonNull
	@Size(min=5, message="Admin username must be at least 5 characters long!")
	private String username;
	@NonNull
	@Size(min=5, message="Admin password must be at least 5 characters long!")
	private String password;
}
