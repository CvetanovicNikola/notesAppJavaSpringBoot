package com.notes.notesApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notes.notesApp.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long>{

}
