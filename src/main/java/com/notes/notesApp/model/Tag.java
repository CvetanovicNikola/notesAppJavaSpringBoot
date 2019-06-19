package com.notes.notesApp.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name="tag")
public class Tag {
	@Id
	@GeneratedValue
	private long tag_id;
	@Size(max=15, message="Tag can not be longer than 15 chracters!")
	private String content;
	@ManyToOne(fetch=FetchType.EAGER)
	@OnDelete(action=OnDeleteAction.CASCADE)
	@JoinColumn(name="note_id")
	private Note note;
}
