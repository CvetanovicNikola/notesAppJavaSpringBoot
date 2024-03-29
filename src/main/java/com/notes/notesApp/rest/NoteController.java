package com.notes.notesApp.rest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.notes.notesApp.exceptions.NoteNotFoundException;
import com.notes.notesApp.model.Note;
import com.notes.notesApp.model.Tag;
import com.notes.notesApp.repository.NoteRepository;
import com.notes.notesApp.repository.TagRepository;
import com.notes.notesApp.utils.EmailUtil;

@RestController
public class NoteController {
	
	@Autowired
	NoteRepository noteRepository;
	@Autowired
	UserController userController;
	@Autowired
	TagRepository tagRepostiory;
	@Autowired
	EmailUtil emailUtil;
	
	@PostMapping("users/{user_id}/notes")
	public Note createNote(@PathVariable long user_id, @RequestBody @Valid Note note) {
		note.setUser(userController.getUserById(user_id));
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL).withZone(ZoneId.systemDefault());
		note.setCreated(timeFormatter.format(LocalDateTime.now()));
		//emailUtil.sendEmail("cvetanovic.nikola@gmail.com", "uspeh!", "USpelo MICA!!!");
		return noteRepository.save(note);
	}
	@GetMapping("users/{user_id}/notes")
	public List<Note> getAllUserNotes(@PathVariable long user_id){
		return noteRepository.findAll().stream()
				.filter(n -> n.getUser().getUser_id() == user_id)
				.collect(Collectors.toList());
			}
	@GetMapping("notes/{note_id}")
	public Note getNote(@PathVariable long note_id) {
		return noteRepository.findById(note_id)
				.orElseThrow(() -> new NoteNotFoundException("No such note!"));
	}
	@PutMapping("notes/{note_id}")
	public Note updateNote(@PathVariable long note_id, @RequestBody @Valid Note note) {
		return noteRepository.findById(note_id)
				.map(n -> {
					n.setContent(note.getContent());
					n.setCreated(note.getCreated());
					return noteRepository.save(n);
				}).orElseThrow(() -> new NoteNotFoundException("No such note!"));
				
	} 
	@DeleteMapping("notes/{note_id}")
	public void deleteNote(@PathVariable long note_id) {
		noteRepository.delete(getNote(note_id));
	}
	@GetMapping("notes/tag/{tag}")
	public List<Note> getNotesByTag(@PathVariable String tag){
		List<Tag> tags = tagRepostiory.findAll().stream()
				.filter(t -> t.getContent().equals(tag))
				.collect(Collectors.toList());
		return tags.stream()
				.map(Tag::getNote)
				.collect(Collectors.toList());
	}
}
