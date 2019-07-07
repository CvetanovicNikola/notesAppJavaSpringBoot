package com.notes.notesApp.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name="note")
public class Note {
	
	@Id
	@GeneratedValue
	private long note_id;
	@NotNull
	@Size(min=3, max=30, message="Note title must be between 3 and 30 characters long!")
	private String title;
	@NotNull
	@Size(min=3, message="A note must be at least 3 characters long!")
	private String content;
	private String created;
	@JsonIgnore
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.REMOVE, orphanRemoval = true, mappedBy = "note")
    private List<Tag> tags = new ArrayList<Tag>();

}
