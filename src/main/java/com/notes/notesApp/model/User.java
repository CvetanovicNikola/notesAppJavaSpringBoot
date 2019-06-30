package com.notes.notesApp.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue
	private long user_id;
	@NotNull
	@Size(min=2, max=50, message="Name must be between 2 and 50 characters long!")
	private String firstName;
	@NotNull
	@Size(min=2, max=50, message="Last name must be between 2 and 50 charactes long!")
	private String lastName;
	@NotNull
	@Size(min=5, max=20, message="Username must be between 5 and 50 characters long!")
	private String username;
	@NotNull
	@Size(min=8, message="password must be between 8 and 20 characters long!")
	private String password;
	@NotNull
	@Email
	private String email;
	//private UserImage userImage;
	private LocalDateTime created;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.REMOVE, orphanRemoval = true, mappedBy = "user")
    private List<Note> notes = new ArrayList<Note>();
	
}
