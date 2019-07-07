package com.notes.notesApp.JSF;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
 
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.notes.notesApp.exceptions.UserNotfoundException;
import com.notes.notesApp.model.Note;
import com.notes.notesApp.model.Tag;
import com.notes.notesApp.model.User;
import com.notes.notesApp.repository.TagRepository;
import com.notes.notesApp.rest.NoteController;
import com.notes.notesApp.rest.TagController;
import com.notes.notesApp.rest.UserController;
import lombok.Data;

@Named("note")
@SessionScoped
@Data
public class NoteBean implements Serializable{
	
	@Inject
	UserBean userBean;
	
	@Autowired
	NoteController noteController;
	
	@Autowired
	TagController tagController;
	
	@Autowired
	UserController userController;
	@Autowired
	TagRepository tagRepository;
	
	private String title;
	private String content;
	private String tagContent;
	private long noteId;
	private String errorMessage;
	private String loggedInUser;
	private String loggedInUserPassword;
	private String loggedInUserEmail;
	private String searchTag;
	private List<String> tags;
	
	

	public long getLoggedUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username= authentication.getName();
		User user = userController.getAllUsers().stream()
				.filter(u -> u.getUsername().equals(username))
				.findFirst()
				.orElseThrow(() -> new UserNotfoundException("No such user!"));
		return user.getUser_id();
	}
	
	public String createNote() {
		try {
			
			Note n = new Note();
			n.setUser(userBean.getUser());
			n.setTitle(title);
			n.setContent(content);
			noteController.createNote(getLoggedUserId(), n);
			Tag tag = new Tag();
			tagContent = tags.stream()
				.collect(Collectors.joining(", "));
			tag.setContent(tagContent);
			tagController.createTag(n.getNote_id(), tag);
			return "allNotes?faces-redirect=true";
			//return "noteDetail.xhtml?noteId=" + n.getNote_id() + "&faces-redirect=true";
		}
		catch (Exception e) {
			errorMessage = e.getMessage();
			return "createNote.xhtml?faces-redirect=true";
		}
		
	}
	
	public void loggedUser() {
		loggedInUser = userController.getUserById(getLoggedUserId()).getUsername();
		loggedInUserPassword = userController.getUserById(getLoggedUserId()).getPassword();
		loggedInUserEmail = userController.getUserById(getLoggedUserId()).getEmail();
		
	}
	
	public List<Note> getAllNotes(){
		List<Note> result = noteController.getAllUserNotes(getLoggedUserId());
		Collections.reverse(result);
		return result;
	}
	
	public String deleteNote(String noteId) {
		noteController.deleteNote(Long.valueOf(noteId));
		return "allNotes?faces-redirect=true";
	}
	public Note getNoteById() {
		long id = Long.valueOf(noteId);
		System.out.println(id);
		return noteController.getNote(id);
	}
	
	public List<Note> getLastFiveNotes(){
		List<Note> result = getAllNotes();
		Collections.reverse(result);
		List<Note> reversedResult = result.subList(Math.max(getAllNotes().size() - 5, 0), getAllNotes().size());
		Collections.reverse(reversedResult);
		return reversedResult;
	}
	
	public List<Note> search(){
	
	
		
		
//		List<Tag> tags = tagRepository.findAll().stream().filter(t ->t.getContent().equals(searchTag)).collect(Collectors.toList());
//		List<Long> notes = tags.stream().filter(n -> n.getTag_id()==)
		return getAllNotes().stream()
				.filter(n -> n.getTitle().toLowerCase().contains(searchTag.toLowerCase())) 
						
				.collect(Collectors.toList());
	}
	

}
