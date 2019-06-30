package com.notes.notesApp.model;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
	private String content;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="note_id")
    private Note note;
}
