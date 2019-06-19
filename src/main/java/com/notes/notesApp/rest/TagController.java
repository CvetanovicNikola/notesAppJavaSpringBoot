package com.notes.notesApp.rest;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.notes.notesApp.exceptions.TagNotFoundException;
import com.notes.notesApp.model.Tag;
import com.notes.notesApp.repository.TagRepository;

@RestController
public class TagController {

		@Autowired
		TagRepository tagRepository;
		@Autowired
		NoteController noteController;
		
		
		@PostMapping("notes/{note_id}/tags")
		public Tag createTag(@PathVariable long note_id, @RequestBody @Valid Tag tag) {
			tag.setNote(noteController.getNote(note_id));
			return tagRepository.save(tag);
		}
		@GetMapping("tags/{tag_id}")
		public Tag getTag(@PathVariable long tag_id) {
			return tagRepository.findById(tag_id)
					.orElseThrow(() -> new TagNotFoundException("no such tag!"));
		}
		@GetMapping("notes/{note_id}/tags")
		public List<Tag> getAllNoteTags(@PathVariable long note_id){
			return tagRepository.findAll().stream()
					.filter(t -> t.getNote().getNote_id() == note_id)
					.collect(Collectors.toList());
					
		}
		@DeleteMapping("tags/{tag_id}")
		public void deleteTag(@PathVariable long tag_id) {
			tagRepository.delete(getTag(tag_id));
		}
}
