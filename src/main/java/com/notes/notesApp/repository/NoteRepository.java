package com.notes.notesApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notes.notesApp.model.Note;

public interface NoteRepository extends JpaRepository<Note, Long>{

}
