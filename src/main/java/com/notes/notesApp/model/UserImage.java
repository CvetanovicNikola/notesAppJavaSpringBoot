package com.notes.notesApp.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user_image")
public class UserImage {
	@Id
	@GeneratedValue
	private long userImage_id;
	private String type;
	private byte[] content;
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	@OnDelete(action=OnDeleteAction.CASCADE)
	private User user;

}
