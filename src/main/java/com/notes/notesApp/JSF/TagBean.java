package com.notes.notesApp.JSF;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import com.notes.notesApp.model.Tag;
import com.notes.notesApp.rest.TagController;
import lombok.Data;


@Named("tag")
@Data
@SessionScoped
public class TagBean implements Serializable{
	
	@Autowired
	TagController tagController;

	
	public List<String> getAllTags(String noteId){
		return tagController.getAllNoteTags(Long.valueOf(noteId)).stream()
				.map(Tag::getContent)
				.collect(Collectors.toList());
	}
}
