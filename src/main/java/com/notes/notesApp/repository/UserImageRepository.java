package com.notes.notesApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notes.notesApp.model.UserImage;

public interface UserImageRepository extends JpaRepository<UserImage, Long>{

}
